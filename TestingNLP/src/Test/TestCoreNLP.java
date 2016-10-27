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
//    	TextProcesser.processer("Consider loop diuretics for treating uid overload or oedema while: an adult, child or young person is awaiting renal replacement therapy, or renal function is recovering in an adult, child or young person not receiving renal replacement therapy.");
    	System.out.println("Complete.");
    }   
}