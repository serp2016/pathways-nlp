package Test;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class QuestionIdentifier 
{
	public static boolean questionFlag(String material)
	{
		boolean questionTag = false;
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(material);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);      
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
				// check the key words to identify question
				if(pos.equals("IN"))
				{
					if((word.toLowerCase().equals("whether"))||(word.toLowerCase().equals("if")))
						questionTag = true;
				}
			}
			// if there is a question found in material
			if(questionTag)
			{
				System.out.println(material); 
				System.out.println("Question inside.");      	
			}
			else
			{
				System.out.println("No question found.");  
			}
		}
      return questionTag;
	}
}
