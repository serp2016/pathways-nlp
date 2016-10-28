package AutoTest;

import AutoTest.Junit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(value = Parameterized.class)
public class textProcesserTest 
{

	    private String guidelineText;
	    private String extraction;

	    // inject via constructor
	    // for instance {"Check immediately whether people currently have chest pain.", "#* Do people currently have chest pain?"}, guidelineText = "Check immediately whether people currently have chest pain.", extraction = "#* Do people currently have chest pain?"
	    public textProcesserTest(String guidelineText, String extraction) 
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
	        	{"Initially assess people for any of the following symptoms, which may indicate an ACS:", "#* Do people have any of the following symptoms, which may indicate an ACS?"},
	        	{"Do not use people's response to glyceryl trinitrate (GTN) to make a diagnosis.", "No question found."},
	        	{"Do not assess symptoms of an ACS differently in men and women. Not all people with an ACS present with central chest pain as the predominant feature.", "No question found."},
	        	{"Do not assess symptoms of an ACS differently in ethnic groups. There are no major differences in symptoms of an ACS among different ethnic groups.", "No question found."},
	        	{"Refer people to hospital as an emergency if an ACS is suspected (see recommendation 1.2.1.3) and: they currently have chest pain or they are currently pain free, but had chest pain in the last 12 hours, and a resting 12-lead ECG is abnormal or not available.", "#* Is an ACS suspected?#* Do they currently have chest pain?#* Do they are currently pain free, but had chest pain in the last 12 hours, and a resting 12-lead electrocardiogram (ECG) is abnormal or not available?)"},
	        	{"If an ACS is suspected (see recommendation 1.2.1.3) and there are no reasons for emergency referral, refer people for urgent same-day assessment if: they had chest pain in the last 12 hours, but are now pain free with a normal resting 12-lead ECG or the last episode of pain was 12–72 hours ago.", "#* Are an ACS suspected ( see recommendation 1.2.1.3 ) and there no reasons for emergency referral?"},
	        	{"Refer people for assessment in hospital if an ACS is suspected (see recommendation 1.2.1.3) and: the pain has resolved and there are signs of complications such as pulmonary oedema. Use clinical judgement to decide whether referral should be as an emergency or urgent same-day assessment.", "#* Is an ACS suspected? #* Is referral as an emergency or urgent same-day assessment?"},
	        	{"If a recent ACS is suspected in people whose last episode of chest pain was more than 72 hours ago and who have no complications such as pulmonary oedema: carry out a detailed clinical assessment (see recommendations 1.2.4.2 and 1.2.4.3) confirm the diagnosis by resting 12-lead ECG and blood troponin level take into account the length of time since the suspected ACS when interpreting the troponin level.Use clinical judgement to decide whether referral is necessary and how urgent this should be.", "#* Whether acute coronary syndrome (ACS) is suspected in people whose last episode of chest pain was more than 72 hours ago and who have no complications such as pulmonary oedema?#* Is referral necessary and how urgent this should be?"},
	        	{"Refer people to hospital as an emergency if they have a recent (confirmed or suspected) ACS and develop further chest pain.", "#* Do they have a recent ( confirmed or suspected ) ACS?"},
	        	{"When an ACS is suspected, start management immediately in the order appropriate to the circumstances (see section 1.2.3) and take a resting 12-lead ECG (see section 1.2.2). Take the ECG as soon as possible, but do not delay transfer to hospital.", "#* Is an ACS suspected?"},
	        	{"If an ACS is not suspected, consider other causes of the chest pain, some of which may be life-threatening (see recommendations 1.2.6.5, 1.2.6.6 and 1.2.6.7).", "#* Is an ACS not suspected?"},
	        	{"Take a resting 12-lead ECG as soon as possible.", "No question found."},
	        	{"When people are referred, send the results to hospital before they arrive if possible.", "#* Are people referred?#* Do possible"},
	        	{"Recording and sending the ECG should not delay transfer to hospital.", "No question found."},
	        	{"Even in the absence of ST-segment changes, have an increased suspicion of an ACS if there are other changes in the resting 12-lead ECG, specifically Q waves and T wave changes. Consider following Unstable angina and NSTEMI (NICE clinical guideline 94) if these conditions are likely. Continue to monitor (see recommendation 1.2.3.4).", "#* Are there other changes in the resting?#* Are these conditions likely?"},
	        	{"Do not exclude an ACS when people have a normal resting 12-lead ECG.", "#* Do people have a normal resting 12-lead ECG?"},
	        	{"If a diagnosis of ACS is in doubt, consider:", "#* Is a diagnosis of ACS in doubt?"},
	        	{"If clinical assessment (as described in recommendation 1.2.1.10) and a resting 12-lead ECG make a diagnosis of ACS less likely, consider other acute conditions. First consider those that are life-threatening such as pulmonary embolism, aortic dissection or pneumonia. Continue to monitor (see recommendation 1.2.3.4)", "#* Do clinical assessment and a resting 12-lead ECG make a diagnosis of ACS less likely?"},
	        	{"If intravenous nitrates are used in specific circumstances, such as for people with concomitant myocardial ischaemia, severe hypertension or regurgitant aortic or mitral valve disease, monitor blood pressure closely in a setting where at least level 2 care[1] can be provided. ", "No question found."},
	        	{"In a person presenting with acute heart failure who is already taking beta-blockers, continue the beta-blocker treatment unless they have a heart rate less than 50 beats per minute, second or third degree atrioventricular block, or shock. ", "#* Is already taking?"},
	        	{"If the angiotensin-converting enzyme inhibitor (or angiotensin receptor blocker) is not tolerated an aldosterone antagonist should still be offered[3].", "#* Is the angiotensin-converting enzyme inhibitor (or angiotensin receptor blocker) not tolerated an aldosterone?"},
	        	{"If the team caring for the patient considers that admission to a critical care area is clinically indicated, then the decision to admit should involve both the consultant caring for the patient on the ward and the consultant in critical care. ", "#* Do the team caring for the patient considers?"},
	        	{"When adults are at risk of acute kidney injury, ensure that systems are in place to recognise and respond to oliguria (urine output less than 0.5 ml/kg/hour) if the track and trigger system (early warning score) does not monitor urine output.", "#* Do the track and trigger system ( early warning score ) does not monitor urine output?"},
	        	{"When adults, children and young people have no identified cause of their acute kidney injury or are at risk of urinary tract obstruction, offer urgent ultrasound of the urinary tract (to be performed within 24 hours of assessment).", "No question found."},
	        	{"Investigate for acute kidney injury, by measuring serum creatinine and comparing with baseline, in children and young people with acute illness if any of the following are likely or present: ", "#* Are any of the following likely or present?"},
	        	{"When adults are at risk of acute kidney injury, ensure that systems are in place to recognise and respond to oliguria (urine output less than 0.5 ml/kg/hour) if the track and trigger system (early warning score) does not monitor urine output. ", "#* Do the track and trigger system ( early warning score ) does not monitor urine output?"},
	        	{"If using a paediatric early warning score, use one with multiple-parameter or aggregate weighted scoring systems that allow a graded response and:", "#* Do using a paediatric early warning score?"},
	        	{"If using a paediatric early warning score, use one with multiple-parameter or aggregate weighted scoring systems that measure: ", "#* Do using a paediatric early warning score?"},
	        	{"When children and young people are at risk of acute kidney injury because of risk factors in recommendation 1.1.2: ", "#* Are children and young people at risk of acute kidney injury?"},
	        	{"Offer intravenous volume expansion to adults having iodinated contrast agents if: they are at increased risk of contrast-induced acute kidney injury because of risk factors in recommendation 1.1.6, or they have an acute illness. ", "No question found."},
	        	{"Consider temporarily stopping ACE inhibitors and ARBs in adults having iodinated contrast agents if they have chronic kidney disease with an eGFR less than 40 ml/min/1.73 m2.", "#* Do they have chronic kidney disease with an eGFR less than 40?"},
	        	{"When acquiring any new CDSS or systems for electronic prescribing, ensure that any systems considered:", "No question found."},
	        	{"Document the results and ensure that appropriate action is taken when results are abnormal. ", "#* Are results abnormal?"},
	        	{"Do not routinely offer ultrasound of the urinary tract when the cause of the acute kidney injury has been identified.", "No question found."},
	        	{"When pyonephrosis (infected and obstructed kidney[s]) is suspected in adults, children and young people with acute kidney injury, offer immediate ultrasound of the urinary tract (to be performed within 6 hours of assessment).", "#* IS pyonephrosis (infected and obstructed kidney[s]) suspected"},
	        	{"When nephrostomy or stenting is used to treat upper tract urological obstruction in adults, children and young people with acute kidney injury, undertake as soon as possible and within 12 hours of diagnosis.", "#* Is nephrostomy or stenting used to treat upper tract urological obstruction in adults?"},
	        	{"Consider loop diuretics for treating uid overload or oedema while: an adult, child or young person is awaiting renal replacement therapy, or renal function is recovering in an adult, child or young person not receiving renal replacement therapy.", "No question found."},
	        	{"Do not refer adults, children or young people to a nephrologist or paediatric nephrologist when there is a clear cause for acute kidney injury and the condition is responding promptly to medical management, unless they have a renal transplant.", "No question found."},
	        	{"Consider discussing management with a nephrologist or paediatric nephrologist when an adult, child or young person with severe illness might bene t from treatment, but there is uncertainty as to whether they are nearing the end of their life.", "#* Are they nearing the end of their life?"},
	        	{"Refer adults, children and young people in intensive care to a nephrology team when there is uncertainty about the cause of acute kidney injury or when specialist management of kidney injury might be needed.", "#* Is there uncertainty about the cause of acute kidney injury?#* Is specialist management of kidney injury needed?"},
	        	{"Consider referral to a paediatric nephrologist for children and young people who have recovered from an episode of acute kidney injury but have hypertension, impaired renal function or 1+ or greater proteinuria on dipstick testing of an early morning urine sample.", "#* Do have recovered from an episode of acute kidney injury but have hypertension?"},
	        	{"When couples have fertility problems, both partners should be informed that stress in the male and/or female partner can affect the couple's relationship and is likely to reduce libido and frequency of intercourse which can contribute to the fertility problems. ", "#* Are couples have fertility problems?"},
	        	{"People who experience fertility problems should be informed that they may nd it helpful to contact a fertility support group.", "#* Do people experience fertility problems?"},
	        	{"Inform people who are using articial insemination to conceive and who are concerned about their fertility that:", "#* Are concerned about their fertility that?"},
	        	{"Inform people who are concerned about their fertility that female fertility (and to a lesser extent) male fertility decline with age. ","#* Are people concerned about their fertility?"},
	        	{"Discuss chances of conception with people concerned about their fertility who are: having sexual intercourse (see table 1) or using artificial insemination (see table 2).", "No question found."},
	        	{"People who are using articial insemination to conceive should have their insemination timed around ovulation.", "#* Are people using articial insemination to conceive?"},
	        	{"Women who smoke should be informed that this is likely to reduce their fertility. ", "#* Do women smoke?"},
	        	{"This document constitutes the Institute's formal guidance on interventions in schools to prevent and reduce alcohol use among children and young people. ", "No question found."},
	        	{"It also looks at how to link these interventions with community initiatives, including those run by children's services.", "#* How to link these interventions with community initiatives"},
	        	{"Alcohol use among children and young people is growing faster than the use of any other drug in the UK and it causes the most widespread problems. Alcohol is also the least regulated and most heavily marketed drug (Advisory Council on the Misuse of Drugs 2006).", "No question found."},
	        	{"The number of children and young people aged 11–15 who drink alcohol has fallen since 2001.", "No question found."},
	        	{"However, those who do drink alcohol consume more – and more often (HM Government 2007).", "No question found."},
	        	{"Children and young people aged 11–15 who regularly smoke or drink are much more likely than non-smokers and non-drinkers to use other drugs (Advisory Council on the Misuse of Drugs 2006).", "#* Do people regularly smoke or drink?"},
	        	{"In 2003 in the UK, 8% of young people aged 15–16 reported having unprotected sex after drinking alcohol (11% females, 6% males). ", "No question found."},
	        	{"In 2000 in Britain, nearly 14% of young people aged 16–19 were estimated to be either mildly (12.4%) or moderately (1.4%) dependent on alcohol, that is, they scored more than 4 on the 'Severity of alcohol dependence questionnaire' (SAD–Q) (Singleton et al. 2000). ", "No question found."},
	        	{"Regular, heavy alcohol consumption and binge drinking are associated with physical health problems, anti-social behaviour, violence, accidents, suicide, injuries and road traf c accidents. ", "No question found."},
	        	{"Alcohol consumption can also have an impact on school performance and crime rates (British Medical Association 2007). ", "No question found."},
	        	{"In 2005–06, over 2500 children aged 0–14 years were admitted to hospital in England with a primary, alcohol-related diagnosis (The Information Centre for Health and Social Care 2006). ", "No question found."},
	        	{"Numerous government strategies and policies aim to prevent or reduce alcohol use among children and young people under 18 (see below).", "No question found."},
	        	{"PHIAC took account of a number of factors and issues in making the recommendations. ", "No question found."},
	        	{"Under UK law, children and young people can consume different types of alcohol in different contexts, depending on their age. ", "No question found."},
	        	{"For instance, young people aged 16 or 17 may consume beer, cider or wine with a meal when under adult supervision on licensed premises. ", "No question found."},
	        	{"In all other circumstances, it is illegal for anyone under 18 to 'knowingly' consume alcohol on licensed premises, or to buy or attempt to buy alcohol. ", "No question found."},
	        	{"It is important that schools take this legal framework into account when planning and delivering alcohol education and when developing partnerships to tackle alcohol issues (within and outside schools).", "#* when planning and delivering alcohol education?"},
	        	{"Different countries favour different approaches to alcohol education.", "No question found."},
	        	{"For example, alcohol use is considered normal for a large proportion of the population in the UK where a 'harm reduction' approach is favoured for young people.", "No question found."}, 
	        	{"By contrast in the US, where most of the research on school-based interventions comes from, abstinence is encouraged among children and young people. ", "No question found."},
	        	{"The renewed national alcohol strategy suggests that, 'more needs to be done to promote sensible drinking'. ", "No question found."},
	        	{"Sensible drinking for adults is described as 'drinking in a way that is unlikely to cause yourself or others signi cant risk of harm' (HM Government 2007).", "No question found."},
	        	{"There is no consensus about what constitutes safe and sensible levels of drinking for children and young people. ", "No question found."},
	        	{"In 2008, the government plans to provide guidance about 'what is and what is not safe and sensible in the light of the latest available evidence from the UK and abroad' (HM Government 2007). ", "No question found."},
	        	{"PHIAC did not, therefore, consider it part of its remit to define these levels.", "No question found."},
	        	{"In the absence of guidance on safe and sensible levels of alcohol consumption, PHIAC focused on encouraging children not to drink, delaying the age at which young people start drinking and reducing the harm it can cause among those who do drink.", "No question found."},
	        	{"The second recommendation acknowledges that some young people may already be drinking harmful amounts of alcohol.", "No question found."},
	        	{"A number of social, cultural and economic factors have an in uence on alcohol consumption among children, young people and parents.", "No question found."},
	        	{"These include peer pressure, the alcohol industry, the media, and the availability and cost of alcohol.", "No question found."},
	        	{"While schools have an important role to play in combating harmful drinking, PHIAC acknowledged that they are limited in terms of what they can achieve (see 3.6 above). ", "No question found."},
	        	{"The Guideline Development Group has made the following recommendations for research, based on its review of evidence, to improve NICE guidance and patient care in the future. ", "No question found."},
	        	{"What are the long-term outcomes of acute kidney injury in adults, children and young people? ", "#* What are the long-term outcomes of acute kidney injury in adults, children and young people? "},
	        	{"Long-term follow-up studies, predominantly from North America, have shown that acute kidney injury is associated with an increased risk of chronic kidney disease or exacerbation of underlying chronic kidney disease. ",  "No question found."},
	        	{"This can lead to end-stage renal disease (ESRD) and long-term dialysis. ", "No question found."},
	        	{"About a quarter to a third of the costs associated with acute kidney injury in adults are due to ESRD. ", "No question found."},
	        	{"Older adults with comorbidities are at particular risk. ", "No question found."},
	        	{"Although acute kidney injury is traditionally regarded as reversible, the psychological effects are not well studied.", "No question found."},
	        	{"Some studies of adults who have recovered from acute kidney injury suggest a reduced quality of life, including higher rates of depression.", "#* Do adults have recovered from acute kidney injury?"},
	        	{"People also often need more social care or discharge to residential care.", "No question found."},
	        	{"The factors associated with the long-term complications of acute kidney injury are poorly understood. ", "No question found."},
	        	{"A large, prospective epidemiological or cohort study is needed with a control group (for example, patients admitted to hospital as an emergency with an acute illness, but without acute kidney injury). ", "No question found."},
	        	{"What is the clinical and cost effectiveness of rapid referral (within 12 hours) to nephrology services for adults with moderate to severe (stage 2 to 3) acute kidney injury not needing critical care?", "#* What is the clinical and cost effectiveness of rapid referral (within 12 hours) to nephrology services for adults with moderate to severe (stage 2 to 3) acute kidney injury not needing critical care?"},
	        	{"There is national variation in referral of patients with moderate to severe acute kidney injury to nephrology services.", "No question found."},
	        	{"Evidence is lacking on the effect of rapid referral (within 12 hours) on major outcomes, including the need for renal replacement therapy, mortality, length of hospital stay and health-related quality of life at 6 months.", "No question found."},
	        	{"In most patients acute kidney injury is managed by correcting volume depletion and hypotension and avoiding further renal insults, including nephrotoxic drugs. ", "No question found."},
	        	{"This does not usually require specialist input from nephrology or critical care services.", "No question found."},
	        	{"The optimal timing for referral to nephrology services is not known.", "No question found."},
	        	{"Rapid referral of all patients with stage 2 to 3 acute kidney injury may allow earlier detection of primary renal diseases and avoid delay in starting appropriate therapy.", "No question found."},
	        	{"It may also ensure more rapid correction of volume depletion and hypotension and initiation of targeted investigations. ", "No question found."},
	        	{"Potential benefits also include prevention of progressive acute kidney injury, avoidance of renal replacement therapy, avoidance of a delayed transfer to critical care, improved chances of renal recovery, a shorter hospital stay and better long-term outcomes.", "No question found."}
	        });
	    }
	    
	@Test
	public void textProcesserExamples() throws SQLException 
	{
			assertThat(Junit.textProcesserExamples(guidelineText), is(extraction));			
//		fail("Not yet implemented");
	}

}
