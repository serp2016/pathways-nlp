package Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import Test.DbConnector;
import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;

import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.util.CoreMap;

public class TestCoreNLP 
{
    public static void main(String[] args) throws SQLException 
    {      
        // read some text in the text variable
    	String extractedText = null;
    	boolean WhoExtractingTag = false;
//    	String guideline_id = "1";
        String text = "People who are using articial insemination to conceive should have their insemination timed around ovulation.";
//		String sql = "insert into questions " + "(question_content,guideline_id)"+"values(?,?)";
//		java.sql.Connection myConn = DbConnector.getConnection();
//		PreparedStatement pstmt = myConn.prepareStatement(sql);
        // check if the text have potential question to be extracted
        if(QuestionIdentifier.questionFlag(text))
        {		    
		    ArrayList<String> input = ConjunctionSplitter.conSplitter(text);
		    for(int counter = 0; counter < input.size();counter++)
		    {
		        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
		        Properties props = new Properties();
		        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
			    Annotation document = new Annotation(input.get(counter));
			    
			    // run all Annotators on this text
			    pipeline.annotate(document);
			    
			    // these are all the sentences in this document
			    // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
			    List<CoreMap> sentences = document.get(SentencesAnnotation.class);	
//				        System.out.println("word\tpos\tlemma\tne");
			    
			    for(CoreMap sentence: sentences) 
			    {
			        // required tokens to transform
			    	boolean extracting = false;
			        String initialToken = "Do";
			        String extraction = "";
			    	String extractedNNS = "";
			        // traversing the words in the current sentence
			        // a CoreLabel is a CoreMap with additional token-specific methods        	
			        for (CoreLabel token: sentence.get(TokensAnnotation.class)) 
			        {
			            // this is the text of the token
			            String word = token.get(TextAnnotation.class);
			            // this is the POS tag of the token
			            String pos = token.get(PartOfSpeechAnnotation.class);
			            // this is the NER label of the token
			            String ne = token.get(NamedEntityTagAnnotation.class);
			            String lemma = token.get(LemmaAnnotation.class);
			            //check if there is "be" inside question.
			            if(pos.startsWith("VB")||pos.startsWith("VBZ"))
			            {
			            	if(lemma.toLowerCase().equals("be"))
			            	{
			            		initialToken = "Is";
			            	}
			            }
			            // check if the subject is plural
			            if(pos.startsWith("VBP"))
			            {
			            	if(lemma.toLowerCase().equals("be"))
			            	{
			            		initialToken = "Are";
			            	}
			            }
			            if(pos.startsWith("NNS"))
			            {
			            	if(word.toLowerCase().equals("people")||word.toLowerCase().equals("women"))
			            	{
			            		extractedNNS = " "+ word.toLowerCase(); 
			            	}
			            }
//			                System.out.println(word+"\t"+pos+"\t"+lemma+"\t"+ne);
			            if(extracting)
			            {
		            		if(word.toLowerCase().matches("[a-z -]+"))
		                	{
		                		if(lemma.equals("be")||pos.equals("MD"))
		                		{
		                			word="";
		                			extraction += word;
		                		}
		                		else
		                		{
		                			extraction += " " + word;
		                		}
		                	}
		            		else
		            		{
		            			extracting = false;
		            		}
//				                if(pos.equals("CC"))
//				                {
//				                	if(word.toLowerCase().matches("and")||word.toLowerCase().matches("or"))
//				                	{
//				                		conjunctionTag = true;
//				                	}
//				                }
			            }
	                    if(pos.equals("IN")||pos.equals("WRB")){
	                    	if((word.toLowerCase().equals("whether"))||(word.toLowerCase().equals("if"))||(word.toLowerCase().equals("when")))
	                    		extracting = true;
	                    }
	                    if(pos.equals("WP"))
	                    {
	                    	if(word.toLowerCase().equals("who"))
	                    	{
	                    		WhoExtractingTag = true;
	                    		extracting = true;
	                    	}
	                    }
			        }
			        if(WhoExtractingTag==true)
			        {
			        	extractedText = initialToken + extractedNNS + extraction + "?";
			        }
			        else
			        {
			        	extractedText = initialToken + extraction + "?";
			        }
			        
//			        pstmt.setString(1, extractedText);
//					pstmt.setString(2, guideline_id);
//					pstmt.executeUpdate();
//					System.out.println("Insert Complete!");
			        System.out.println(extractedText);
//			            // this is the Stanford dependency graph of the current sentence
//			            SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
//			            System.out.println(dependencies);
			    }
			    // This is the coreference link graph
			    // Each chain stores a set of mentions that link to each other,
			    // along with a method for getting the most representative mention
			    // Both sentence and token offsets start at 1!
//				        Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
		    }
        }
    }
}