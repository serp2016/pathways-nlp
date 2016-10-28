# Intelligence Information Extraction System for Clinical Orientation(IIES)

Natural Language Processing (NLP) is a process to make computer understand human language input and get output base on human understanding requirements.  In the clinical healthcare today, clinical experts have to find the questions, answers and conditions from tons of clinical guidelines, and use these information to create clinical pathways.  The Intelligent Information Extraction System (IIES) is designed to help clinical experts resolve the difficulties to find useful information from clinical guidelines.  The research has been done based on the comparison between the existing Natural Language Processing Toolkits.  The preliminary results of the research show that stanford CoreNLP suite is the richest NLP tool to implementing IIES, and the distributed database benefits the quick processing.  Latest version of the system is able to identifying nearly all the domain questions, and the majority of identified questions are provided after the processing.  According to experience of users, IIES was useful and obtained acceptance in target condition.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Download the XAMPP(https://www.apachefriends.org/index.html)

Download IIES source code from our repository

Put ‘IIESWebsite’ folder in this directory 
```
/Applications/XAMPP/xamppfiles/htdocs
```




### Run IIES

Run XAMPP Apache server(make sure the port is 80).

Change the directory path of "IIESProcessor.jar" in "testexec.php" if the path is different. (Commented out in testexec.php)


You can upload pdf files into uploads folder, choose a pdf file from your PC and click upload, the processor will start extracting questions.(May take about one and half hour to process the guideline). 

After the extraction finished, enter this url into browser’s address bar.
```
localhost/IIESWebsite/search.php
```



Search keywords like "chest pain" or "ACS" or "do people have a normal resting".


Some results will be listed under the search bar, then, click "Check Source" to see the source pdf page.




**Important Stuffs** 

1. Operating System must be macOS X

2. Our IIES need a long time to process sentencens from the guideline, so, it should take about one and a half hour to process this "Clinical Guideline - Chest pain of recent onset.pdf" guideline.

3. Our pdf highlight function is in a trail, it will be expired on 9th Nov.

4. It may take about 30s to display the pdf page after you click "chech source" button, please be patient.

5. Don't shut down your PC when the processor is working.

6. Our core NLP part has been packaged as a jar file named as "IIESProcessor.jar"

7. You probably get some errors with "ionCube" when you click "Check source button", please visit this website(http://www.ioncube.com/loaders.php) to download the loader packages for OS X (64bit), the "readme" file in that package folder would be helpful.

8. If you can't get any results after searching, use this file to start processing again.

Enter this url into browser’s address bar. 
```
localhost/IIESWebsite/testexec.php
```
Click "sumbit" button, the processor will start extracting questions.(May take about one and half hour to process the guideline). 

## Built With

* [PHP](http://php.net/) - The front end used
* [Stanford CoreNLP Toolkits](http://stanfordnlp.github.io/CoreNLP/) - Core NLP Library
* [setaPDF-Extractor](https://www.setasign.com/products/setapdf-extractor/demos/get-words/#p-368) - Used to Highlight sentences


## Authors

* **He Jiang** 
* **JingWen Wei** 

Natural Language Processing is our main part, the user interface may not working because of different local configure settings. The project works well in the Ingenuity 2016.

Please have no hesitate on contacting us if you have any questions. My email: a1684889@student.adelaide.edu.au

