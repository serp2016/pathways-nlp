package AutoTest;

import java.sql.SQLException;
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
	// this test is to test if questions have been correctly identified
	public static boolean questionIdentifier(String material)
	{
		return Test.QuestionIdentifier.questionFlag(material);
	}
	
	// this method is to test if the question has been transformed into right format
	public static String chestPainExamples(String guidelineText) throws SQLException
	{
        return Test.TextProcesser.processer(guidelineText,"111");
	}
}
