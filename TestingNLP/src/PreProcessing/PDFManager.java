package PreProcessing;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFManager {
    
   private PDFParser parser;
   private PDFTextStripper pdfStripper;
   private PDDocument pdDoc ;
   private COSDocument cosDoc ;
   
   private String Text;
   private String filePath;
   private File file;
   private int filePage;
   
   public PDFManager() 
   {
        
   }
   
   public String GetText(int pageNum) throws IOException
   {
       pdfStripper.setStartPage(pageNum);
       pdfStripper.setEndPage(pageNum);     
       // if you want to get text from full pdf file use this code
       // pdfStripper.setEndPage(pdDoc.getNumberOfPages());      
       Text = pdfStripper.getText(pdDoc);
       return Text;
   }
   
   public void initialPDF() throws IOException
   {
       this.pdfStripper = null;
       this.pdDoc = null;
       this.cosDoc = null;
       
       file = new File(filePath);
       parser = new PDFParser(new RandomAccessFile(file,"r"));
       parser.parse();
       cosDoc = parser.getDocument();
       pdfStripper = new PDFTextStripper();
       pdDoc = new PDDocument(cosDoc);
   }
   public void closePDF() throws IOException
   {
	   this.cosDoc.close();
	   this.pdDoc.close();
   }
   
   public int pageRange() throws IOException
   {
       filePage = pdDoc.getNumberOfPages(); 
       return filePage;
   }
   
   public void setFilePath(String filePath) 
   {
        this.filePath = filePath;
   }
   
}