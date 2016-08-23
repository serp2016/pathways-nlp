package AutoTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import Test.ConjunctionSplitter;
import Test.QuestionIdentifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class Junit 
{
	public static boolean questionTag(String guidelineText)
	{
		return true;
	}
	public static String chestPainExamples(String guidelineText)
	{
		String questionExtraction = "";
        // check if the text have potential question to be extracted
        if(QuestionIdentifier.questionFlag(guidelineText))
        {		    
		    ArrayList<String> input = ConjunctionSplitter.conSplitter(guidelineText);
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
			    for(CoreMap sentence: sentences) 
			    {
			        // switch of extracting action
			    	boolean extracting = false;
			        String initialToken = "Do";
			        String extraction = "";
			        // traversing the words in the current sentence
			        // a CoreLabel is a CoreMap with additional token-specific methods        	
			        for (CoreLabel token: sentence.get(TokensAnnotation.class)) 
			        {
			            // this is the text of the token
			            String word = token.get(TextAnnotation.class);
			            // this is the POS tag of the token
			            String pos = token.get(PartOfSpeechAnnotation.class);
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
			            if(extracting)
			            {
		            		if(word.toLowerCase().matches("[a-z ]+"))
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
			            }
	                    if(pos.equals("IN")){
	                    	if((word.toLowerCase().equals("whether"))||(word.toLowerCase().equals("if")))
	                    		extracting = true;
	                    }
			        }
			        	System.out.println(initialToken + extraction + "?");
			        	questionExtraction += "#* " + initialToken + extraction + "?";
			    }
		    }
        }
        else
        {
        	questionExtraction = "No question found.";
        }
        return questionExtraction;
	}

}
