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

	    // inject via constructor
	    // for instance {"Check immediately whether people currently have chest pain.", "#* Do people currently have chest pain?"}, guidelineText = "Check immediately whether people currently have chest pain.", extraction = "#* Do people currently have chest pain?"
	    public chestPainExamplesTest(String guidelineText, String extraction) 
	    {
	        this.guidelineText = guidelineText;
	        this.extraction = extraction;
	    }

		// multiple parameters, uses Collection<Object[]>, user can add new test cases in here.
	    @Parameters(name = "{index}: ({1}) extracted from ({0})")
	    public static Collection<Object[]> data() {
	        return Arrays.asList(new Object[][]{
	                {"Check immediately whether people currently have chest pain. ", "#* Do people currently have chest pain?"},
	                {"Determine whether the chest pain may be cardiac and therefore whether this guideline is relevant, by considering: ", "#* Is the chest pain cardiac?#* Is this guideline relevant?"},
	                {"Initially assess people for any of the following symptoms, which may indicate an ACS:", "#* Do people have any of the following symptoms, which may indicate an acute coronary syndrome (ACS)?"},
	                {"Do not use people's response to glyceryl trinitrate (GTN) to make a diagnosis.", "No question found."},
	                {"Do not assess symptoms of an ACS differently in men and women. Not all people with an ACS present with central chest pain as the predominant feature.", "No question found."},
	                {"Do not assess symptoms of an ACS differently in ethnic groups. There are no major differences in symptoms of an ACS among different ethnic groups.", "No question found."},
	                {"Refer people to hospital as an emergency if an ACS is suspected (see recommendation 1.2.1.3) and: they currently have chest pain or they are currently pain free, but had chest pain in the last 12 hours, and a resting 12-lead ECG is abnormal or not available.", "#* Is an acute coronary syndrome (ACS) suspected?#* Do they currently have chest pain?#* Do they are currently pain free, but had chest pain in the last 12 hours, and a resting 12-lead electrocardiogram (ECG) is abnormal or not available?)"},
	                {"If an ACS is suspected (see recommendation 1.2.1.3) and there are no reasons for emergency referral, refer people for urgent same-day assessment if: they had chest pain in the last 12 hours, but are now pain free with a normal resting 12-lead ECG or the last episode of pain was 12–72 hours ago.", "#* Whether an acute coronary syndrome (ACS) is suspected?#* Whether there are no reasons for emergency referral?#* Did they have chest pain in the last 12 hours, but are now pain free with a normal resting 12-lead electrocardiogram (ECG)? or the last episode of pain was 12–72 hours ago?"},
	                {"Refer people for assessment in hospital if an ACS is suspected (see recommendation 1.2.1.3) and: the pain has resolved and there are signs of complications such as pulmonary oedema. Use clinical judgement to decide whether referral should be as an emergency or urgent same-day assessment.", "#* Is an acute coronary syndrome (ACS) suspected? #* Is referral as an emergency or urgent same-day assessment?"},
	                {"If a recent ACS is suspected in people whose last episode of chest pain was more than 72 hours ago and who have no complications such as pulmonary oedema: carry out a detailed clinical assessment (see recommendations 1.2.4.2 and 1.2.4.3) confirm the diagnosis by resting 12-lead ECG and blood troponin level take into account the length of time since the suspected ACS when interpreting the troponin level.Use clinical judgement to decide whether referral is necessary and how urgent this should be.", "#* Whether acute coronary syndrome (ACS) is suspected in people whose last episode of chest pain was more than 72 hours ago and who have no complications such as pulmonary oedema?#* Is referral necessary and how urgent this should be?"},
	                {"Refer people to hospital as an emergency if they have a recent (confirmed or suspected) ACS and develop further chest pain.", "#* Do people have a recent acute coronary syndrome (ACS) and develop further chest pain?"},
	                {"When an ACS is suspected, start management immediately in the order appropriate to the circumstances (see section 1.2.3) and take a resting 12-lead ECG (see section 1.2.2). Take the ECG as soon as possible, but do not delay transfer to hospital.", "#* Is an acute coronary syndrome (ACS) suspected?"},
	                {"If an ACS is not suspected, consider other causes of the chest pain, some of which may be life-threatening (see recommendations 1.2.6.5, 1.2.6.6 and 1.2.6.7).", "#* Is an acute coronary syndrome (ACS) suspected?"},
	                {"Take a resting 12-lead ECG as soon as possible.", "No question found."},
	                {"When people are referred, send the results to hospital before they arrive if possible.", "#* Are people referred?"},
	                {"Recording and sending the ECG should not delay transfer to hospital.", "No question found."},
	                {"Even in the absence of ST-segment changes, have an increased suspicion of an ACS if there are other changes in the resting 12-lead ECG, specifically Q waves and T wave changes. Consider following Unstable angina and NSTEMI (NICE clinical guideline 94) if these conditions are likely. Continue to monitor (see recommendation 1.2.3.4).", "#* Are there other changes in the resting 12-lead ECG?"},
	                {"Do not exclude an ACS when people have a normal resting 12-lead ECG.", "#* Do people have a normal resting 12-lead ECG?"},
	                {"If a diagnosis of ACS is in doubt, consider:", "#* Is a diagnosis of ACS in doubt?"},
	                {"If clinical assessment (as described in recommendation 1.2.1.10) and a resting 12-lead ECG make a diagnosis of ACS less likely, consider other acute conditions. First consider those that are life-threatening such as pulmonary embolism, aortic dissection or pneumonia. Continue to monitor (see recommendation 1.2.3.4)", "#* Do clinical assessment and a resting 12-lead ECG make a diagnosis of ACS less likely?"}
	        });
	    }
	    
	@Test
	public void testChestPainExamples() 
	{
			assertThat(Junit.chestPainExamples(guidelineText), is(extraction));			
//		fail("Not yet implemented");
	}

}
