# Intelligence Information Extraction System for Clinical Orientation(IIES)

Natural Language Processing (NLP) is a process to make computer understand human language input and get output base on human understanding requirements.  In the clinical healthcare today, clinical experts have to find the questions, answers and conditions from tons of clinical guidelines, and use these information to create clinical pathways.  The Intelligent Information Extraction System (IIES) is designed to help clinical experts resolve the difficulties to find useful information from clinical guidelines.  The research has been done based on the comparison between the existing Natural Language Processing Toolkits.  The preliminary results of the research show that stanford CoreNLP suite is the richest NLP tool to implementing IIES, and the distributed database benefits the quick processing.  Latest version of the system is able to identifying nearly all the domain questions, and the majority of identified questions are provided after the processing.  According to experience of users, IIES was useful and obtained acceptance in target condition.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Prerequisites

Download the XAMPP(https://www.apachefriends.org/index.html)

Download IIES source code from the master repository

Put ‘IIESWebsite’ folder in this directory 
```
/Applications/XAMPP/xamppfiles/htdocs
```
Unzip "Library.zip" and put that folder into "IIESWebsite" folder

Download IIESprocessor.jar from Slack (#pathways-nlp channel) uploaded on 31 October 2016.


### Run IIES

Run XAMPP Apache server(make sure the port is 80).

Change the directory path of "IIESProcessor.jar" in "testexec.php" if the path is different. (Commented out in testexec.php)

Enter this url into browser’s address bar.
```
localhost/IIESWebsite/search.php
```
Option 1(first):

A guideline was already upladed and saved in the database to facilitate the demostration of search function. HOWEVER, the check source function is unavaliable due to the filepath must be user's local file. MOST IMPORTANTLY, the guideline database and sentence database will be totally deleted until user fully finished the upload fils process in option 2. Therefore try to search some content before uploading new guidelines.

Search keywords like "chest pain" or "ACS" or "do people have a normal resting".


Some results will be listed under the search bar, then, click "Check Source" to see the source pdf page.

Option 2:

You can upload pdf files into uploads folder, choose a pdf file from your PC and click upload, the processor will start extracting questions.(May take about one and half hour to process each guideline). 

After the extraction finished, the search function will be avaliable again and the new extracted questions is able to check source.

NOTE: Due to the communication with Clinical Pathway Creator, the questions database will not erase even the user uploaded new guidelines, therefore the search function will display the former uploaded questions.


**Important Stuffs** 

1. Operating System must be macOS X

2. The IIES need a long time to process sentencens from the guideline, so, it should take about one and a half hour to process this "Clinical Guideline - Chest pain of recent onset.pdf" guideline.

3. The pdf highlight function is currently in a trail, it will be expired on 9th Nov 2016.

4. It may take about 30s to display the pdf page after you click "chech source" button, please be patient.

5. Do not shut down your PC when the processor is working.

6. Our core NLP part has been packaged as a jar file named as "IIESprocessor.jar"

7. You probably get some errors with "ionCube" when you click "Check source button", please visit this website(http://www.ioncube.com/loaders.php) to download the loader packages for OS X (64bit), the "readme" file in that package folder would be helpful.

8. If you can't get any results after searching, use this file to start processing again.

Enter this url into browser’s address bar. 
```
localhost/IIESWebsite/testexec.php
```
Click "sumbit" button, the processor will start extracting questions.(May take about one and half hour to process the guideline). 

## Built With

* [PHP](http://php.net/) - Back end
* [Bootstrap, HTML, Css] - Front end
* [Stanford CoreNLP Toolkits](http://stanfordnlp.github.io/CoreNLP/) - Core NLP Library
* [setaPDF-Extractor](https://www.setasign.com/products/setapdf-extractor/demos/get-words/#p-368) - Used to Highlight sentences


## Authors

* **He Jiang** 
* **JingWen Wei** 

Natural Language Processing is our main part, the user interface may not working because of different local configure settings. This project worked well in the Ingenuity 2016.

Please have no hesitate on contacting us if you have any questions. My email: a1684889@student.adelaide.edu.au ; jingwen.wei@student.adelaide.edu.au

