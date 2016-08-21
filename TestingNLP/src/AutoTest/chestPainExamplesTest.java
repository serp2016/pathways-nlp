package AutoTest;

import AutoTest.Junit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(value = Parameterized.class)
public class chestPainExamplesTest 
{

	    private String guidelineText;
	    private String extraction;

	    // Inject via constructor
	    // for {"Check immediately whether people currently have chest pain.", "#* Do people currently have chest pain?"}, guidelineText = "Check immediately whether people currently have chest pain.", extraction = "#* Do people currently have chest pain?"
	    public chestPainExamplesTest(String guidelineText, String extraction) 
	    {
	        this.guidelineText = guidelineText;
	        this.extraction = extraction;
	    }

		// multiple parameters, uses Collection<Object[]>
	    @Parameters(name = "{index}: ({1}) extracted from ({0})")
	    public static Collection<Object[]> data() {
	        return Arrays.asList(new Object[][]{
	                {"Check immediately whether people currently have chest pain. ", "#* Do people currently have chest pain?"},
	                {"Determine whether the chest pain may be cardiac and therefore whether this guideline is relevant, by considering: ", "#* Is the chest pain cardiac?#* Is this guideline relevant?"},
	                {"Do not use people's response to glyceryl trinitrate (GTN) to make a diagnosis.", "No question found."}
	        });
	    }
	@Test
	public void testChestPainExamples() 
	{
			assertThat(Junit.chestPainExamples(guidelineText), is(extraction));			
//		fail("Not yet implemented");
	}

}
