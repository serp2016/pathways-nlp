package Test;

import java.io.IOException;
import java.sql.SQLException;

import Test.DbConnector;

public class TestCoreNLP 
{
	public static boolean guidelineProcessingIndicator = false;
    public static void main(String[] args) throws SQLException, IOException 
    {
//    	PreProcessing.PreProcessing.preprocesser("/Users/Charles/Desktop/MSE5 Project/Final/Guidelines");
    	DbConnector.sentencesTableLoader();
    	guidelineProcessingIndicator = false;
//    	int rowCounter = DbConnector.sentencesTableRowCounter();
//    	for(int i =0; i<=rowCounter-1;i++)
//    	{
//    		String text = inputTextSet.get(2*i);
//    		int guideline_id = 0;
//            TextProcesser.processer(text,guideline_id);
//    	}
    }   
}