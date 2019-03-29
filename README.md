# Talent test automation framework

Test automation framework to test Talent system's flows 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them

 - Java 
 - Soucelab access (Optional)
 - Selenium grid (Optional)
 - Intellij 


### Project Structure 

- src
  - main
     - java
       - dto
         - Note: the dto package contains a set of models used to store information about the website. 
       - pages 
          - Note: the pages package contains a set of interfaces and implementation that handle interaction with the application)
         - web 
         - mobile (TBI)
       - utils
         - Note: utilities package is the one that contains logic to start the webdriver as well as handling the creation of the pages  
     - resources
  - test
    - java 
       - dashboard 
         - a way to organize the test this package structure might change depending of the needs of the project, but its use is to store all classes related to test
       - generic 
          - Note:  contains base class and listener for the report creation 
  

### Installing

1. download the repository using git clone command 

```
git clone https://github.com/rafcasto/talent-ui-test-automation.git

```

2. using Intellij IDE, open the project 

3. in the project go to the test folder -> resources -> right click on the TalentTestSuite.xml file to execute all the test
## Running the tests

you can run all the test at once thu IDE by right clicking on the TalentTestSuite.xml or execute one by one 

test can also be executed by command line 

```
 ./gradlew test
```

### test configuration 

test can be run on two diferent enviroments dev and qa, this this value can be change on the config.properties file located in the project folder src/test/resources/config.properties


```
environment=qa
```

the settings of the browser or if the test is executed locally, as part of the selenium grid or in cloud 
can be found in the properties files related to each environment "dev-environment.properties" or "qa-environment.properties"


```
url=https://test.torshtalent.com/
selenium.hub=http://localhost
selenium.port=4444
browsername=firefox
executionmode=local
soucelab.username=rafcasto
soucelab.accessKey=000ed4b3-fa26-47e2-8eed-5656ebd0dbb5
soucelab.platform=macOS 10.13
soucelab.version=73
soucelab.build=Onboarding Sample App - Java
soucelab.name=2-user-site
soucelab.url=https://ondemand.saucelabs.com/wd/hub
```

the "executionmode" property determine if the test will be executed locally or in the cloud 
this property can accept soucelab or local, the test will be run by default in the local machine 
