package Test;

import java.io.IOException;
import java.sql.SQLException;

import Test.DbConnector;

public class TestCoreNLP 
{
    public static void main(String[] args) throws SQLException, IOException 
    {
    	PreProcessing.PreProcessing.preprocesser(args[0]);
    	DbConnector.sentencesTableLoader(); 
//    	DbConnector.clearDatabase("questions");
//    	DbConnector.clearDatabase("sentence_questions");  	
//    	TextProcesser.processer("Even in the absence of ST-segment changes, have an increased suspicion of an ACS if there are other changes in the resting 12-lead ECG, specifically Q waves and T wave changes. Consider following Unstable angina and NSTEMI (NICE clinical guideline 94) if these conditions are likely. Continue to monitor (see recommendation 1.2.3.4).");
    	System.out.println("Complete.");
    }   
}