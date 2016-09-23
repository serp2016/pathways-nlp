package Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.util.CoreMap;

public class ConjunctionSplitter
{

	public static ArrayList<String> conSplitter(String inputsentence)
	{
		ArrayList<String> output = new ArrayList<String>();
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(inputsentence);
	        
	        // run all Annotators on this text
	    pipeline.annotate(document);
	        
	        // these are all the sentences in this document
	        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
	    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	    for(CoreMap sentence: sentences) 
	    {
	    	// this is the parse tree of the current sentence
	    	Tree tree = sentence.get(TreeAnnotation.class);
	    	
	    	TregexPattern SBARpattern = TregexPattern.compile("@SBAR >> @SBAR");
	    	TregexMatcher SBARmatcher = SBARpattern.matcher(tree);
	    	TregexPattern NPpattern = TregexPattern.compile("@NP > (@S > @ROOT)");
	    	TregexMatcher NPmatcher = NPpattern.matcher(tree);
	    	
	    	if(NPmatcher.findNextMatchingNode())
	    	{
	    		NPmatcher = NPpattern.matcher(tree);
		    	while(NPmatcher.findNextMatchingNode())
		    	{
		    		Tree match = NPmatcher.getMatch();
		    		if(QuestionIdentifier.questionFlag(Sentence.listToString(match.yield())))
		    		output.add(Sentence.listToString(match.yield()));
		    	}
	    	}
	    	else{
	    	if(SBARmatcher.findNextMatchingNode())
	    	{
	    		SBARmatcher = SBARpattern.matcher(tree);
		    	while(SBARmatcher.findNextMatchingNode())
		    	{
		    		Tree match = SBARmatcher.getMatch();
		    		if(QuestionIdentifier.questionFlag(Sentence.listToString(match.yield())))
		    		output.add(Sentence.listToString(match.yield()));
		    	}
	    	}
	    	else
	    	{
		    	SBARpattern = TregexPattern.compile("@SBAR >> @ROOT");
		    	SBARmatcher = SBARpattern.matcher(tree);
		    	
		    	while(SBARmatcher.findNextMatchingNode())
		    	{
		    		Tree match = SBARmatcher.getMatch();
		    		if(QuestionIdentifier.questionFlag(Sentence.listToString(match.yield())))
		    		output.add(Sentence.listToString(match.yield()));
		    	}
	    	}
		}
	    }
		return output;
	}
	
}
