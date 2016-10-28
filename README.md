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
!!important!! 
1. Operating System must be macOS
2. Our IIES need a long time to process sentencens from the guideline, so, it should take about one and a half hour to process this "Clinical Guideline - Chest pain of recent onset.pdf" guideline.

### Run IIES

Run XAMPP Apache server(make sure the port is 80)

Enter this url into browser’s address bar
```
localhost/IIESWebsite/search.php
```



```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds


## Authors

* **He Jiang** 
* **JingWen Wei** 

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.


