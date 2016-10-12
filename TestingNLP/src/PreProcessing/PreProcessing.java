package PreProcessing;

import java.io.*;
import java.sql.SQLException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Test.DbConnector;

public class PreProcessing 
{	
	private Vector<String> GuidelineDatabase = new Vector<String>();
	
	// read the path that the user try to insert and put all pdf files into next steps
	public void readPath(String inputPath)	throws IOException, SQLException
	{
		File folder = new File(inputPath);
		File[] listOfFiles = folder.listFiles();
		Pattern pdf = Pattern.compile("(\\.pdf)$");
		
		// the case of wrong path
		if(listOfFiles.length < 1)
		{
			System.out.println("No file found or wrong directory");
		}
		// clear all databases
		DbConnector.clearDatabase("guidelines");
		DbConnector.clearDatabase("sentences");
		
		// read all the files and saves in vector
		for (File file : listOfFiles) 
		{
			if (file.isFile()) 
			{
				// check if the file is a pdf file
				Matcher m = pdf.matcher(file.getName());
				if(m.find())
				{
					Test.TestCoreNLP.guidelineProcessingIndicator = true;
					DbConnector.guidelineTableInserter(file.getName(), inputPath);
					System.out.println(file.getName() + " inserted into database.");
					String tmpStr = inputPath + "/" + file.getName();
					this.GuidelineDatabase.add(tmpStr);					
				}
			}
		}		
	}
	
	public void readFile() throws IOException, SQLException
	{		
		// read each pdf files to convert to text files
		for(int i = 0; i < this.GuidelineDatabase.size(); i++)
		{
			int pages;
			PDFManager pdfManager = new PDFManager();
			pdfManager.setFilePath(this.GuidelineDatabase.get(i));
			pdfManager.initialPDF();
			pages = pdfManager.pageRange();
		    for(int j = 1; j <= pages; j++)
		    //Read all the text file to be processed
		    {
		    	
	            File file = new File(this.GuidelineDatabase.get(i).toString() + j + ".txt");
	            file.createNewFile();
	            FileWriter fw = new FileWriter(file);
	            BufferedWriter bw = new BufferedWriter(fw);
	            bw.write(pdfManager.GetText(j));
	            bw.flush();
	            bw.close();
		    }
		    pdfManager.closePDF();
		    removeFormat(this.GuidelineDatabase.get(i).toString(),pages);
		}
	}
	
	public void removeFormat(String guidelinePath, int pages) throws IOException, SQLException
	{
		int startPage = 1;
		String guidelineName = "";
		String outputPath = "";
	    for(int i = 1; i <= pages; i++)
	    	//Write all the PDF guideline into text files
	    {
            File file = new File(guidelinePath + i + ".txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String paragraph = "";
    		String lineToRemove1 = "© NICE";
    		String currentLine = "";
    		if(i == 1)
    		{
    			outputPath = file.getParent();
    			while ((currentLine = br.readLine()) != null)
    			{
    				if(currentLine.contains("guideline"))break;
    				paragraph = paragraph + currentLine + " ";
    			}
    			guidelineName = paragraph;
    			guidelineName = guidelineName.trim().replaceAll(" +", "_");
    			DbConnector.guidelineNameUpdate(file.getName().substring(0, file.getName().length() - 5), guidelineName);
    		}
    	    while ((currentLine = br.readLine()) != null) 
    	    {
    	    	if(currentLine.contains(lineToRemove1)) continue;
    	    	else if(currentLine.contains("........."))
    	    		// skip content pages
    	    	{
    	    		startPage = i;
    	    	}
    	    	else
    	    	{
    	    		paragraph = paragraph + currentLine + " ";
    	    	}
    	    } 	    
            br.close();
    		file.delete();
    		
    		// process the original text file
            String outputFileName = guidelineName + "_Page_" + i + "_modified.txt";
            // jump content pages
            if(startPage == 1)continue;
            if(i == startPage)continue;
            // saved 
			textProcess(outputFileName, outputPath, paragraph);
			
			// insert into database
            File modifiedFile = new File(outputPath + "/" + outputFileName);
            FileReader mfr = new FileReader(modifiedFile);
            BufferedReader mbr = new BufferedReader(mfr);
            String sentenceCon = "";
            int sentencePage = i;
            int guideline_id = DbConnector.guidelineID(guidelineName);
            while ((currentLine = mbr.readLine()) != null) 
            {
            	sentenceCon = currentLine;
            	DbConnector.sentenceTableInserter(sentenceCon, sentencePage, guideline_id);
            }
            mbr.close();
    		modifiedFile.delete();
//			System.out.println("File: " + guidelineName + i +" Done");
	    }
	}
	
	public static void textProcess(String outputFileName, String outputPath, String paragraph) throws IOException 
	{
		String sentence = "";
		try(PrintWriter out = new PrintWriter( outputPath + "/" + outputFileName ))
		{
			for(int i = 0; i < paragraph.length(); i++)
			{
				char tmp = paragraph.charAt(i);
				if(tmp != '.' && i != paragraph.length() - 1)
				{
					sentence = sentence + tmp;
				}
				else if(tmp == '.')
				{
					if(sentence.length() >= 1)
					{
						if(!Character.isDigit(sentence.charAt(sentence.length() - 1)) && sentence.charAt(sentence.length() - 1) != '.')
						{
							sentence = sentence + tmp;
						    out.println(sentence);
						    sentence = "";
						}
						else
						{
							sentence = sentence + tmp;
						}
					}
					else
					{
						sentence = sentence + tmp;
					}
				}
				else if(i == paragraph.length() - 1)
				{
					sentence = sentence + tmp;
				    out.println(sentence);
				    sentence = "";
				}
			}
		}
	}
	
	// method runs the project
	public static void preprocesser(String filePath) throws IOException, SQLException
	{
		// initial the processing
	    PreProcessing preP = new PreProcessing();
	    preP.readPath(filePath);
	    preP.readFile();
		System.out.println("Preprocessing complete!");
	}
	
}
