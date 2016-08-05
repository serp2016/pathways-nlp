package Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class TestCoreNLP 
{
    public static void main(String[] args) 
    {
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        
        // read some text in the text variable
        String text = "Determine whether the chest pain may be cardiac";
        
        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);
        
        // run all Annotators on this text
        pipeline.annotate(document);
        
        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        boolean questionTag = false;
        String initialToken = "Do";
        String extraction = "";
        System.out.println("word\tpos\tlemma\tne");
        for(CoreMap sentence: sentences) 
        {
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
                if(pos.startsWith("VBP"))
                {
                	if(lemma.toLowerCase().equals("be"))
                	{
                		initialToken = "Are";
                		}
                }
                System.out.println(word+"\t"+pos+"\t"+lemma+"\t"+ne);
                if(questionTag)
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
                }
                // check the key words to identify question
                if(pos.equals("IN"))
                {
                	if((word.toLowerCase().equals("whether"))||(word.toLowerCase().equals("if")))
                		questionTag = true;
                }
            }
            if(questionTag)
            {
            	System.out.println("Question inside.");
            	System.out.println(initialToken + extraction + "?");
            	
            }
//            // this is the parse tree of the current sentence
//            Tree tree = sentence.get(TreeAnnotation.class);
//            System.out.println(tree);
//            
//            // this is the Stanford dependency graph of the current sentence
//            SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
//            System.out.println(dependencies);
//        }
//        // This is the coreference link graph
//        // Each chain stores a set of mentions that link to each other,
//        // along with a method for getting the most representative mention
//        // Both sentence and token offsets start at 1!
//        Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
        }
    }
}