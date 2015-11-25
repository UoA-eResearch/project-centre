# Project-Centre

A lean and mean service to manage research projects for the Centre for eResearch at the University of Auckland.

## Requirements

 - Java 8
 - maven (version >= 3)

## Big TODOs

 - more documentation
 - Exception handling
 - write unit & integration tests
 
## Development

### Code style

Google code style for Java is used:
 
 - https://google.github.io/styleguide/javaguide.html
 - https://github.com/google/styleguide
 - XML style file for Eclipse: https://raw.githubusercontent.com/google/styleguide/gh-pages/eclipse-java-google-style.xml
 - XML style file for Intellij Idea: https://raw.githubusercontent.com/google/styleguide/gh-pages/intellij-java-google-style.xml
 - Also, checking the style part of the build cycle (not fully configured yet though): https://github.com/checkstyle/checkstyle
 - XML checkstyle check file: https://raw.githubusercontent.com/checkstyle/checkstyle/master/src/main/resources/google_checks.xml => develop/google_checks.xml
 - checkstyle integration as a git hook: https://gist.github.com/davetron5000/37350 also interesting: https://www.link-intersystems.com/blog/2014/12/07/a-git-checkstyle-pre-receive-hook/

### Git history

Use rebase when pulling if possible, we don't want a dirty git history.

### Getting started

    git clone https://github.com/UoA-eResearch/project-centre.git
    cd project-centre
    
#### Run using maven

    # we use the 'dev' profile for development
    mvn  spring-boot:run  -Dspring.profiles.active="dev"
    
#### Run using java

    # we use the 'dev' profile for development
    mvn clean package
    java -Dspring.profiles.active="dev" -jar target/projects_centre.jar
    
#### Profiles

We use spring profiles to mix & match application startup environments. Startup jobs are run via CommandLineRunners, 
located under the .../projectcentre/util/clRunners package.

Currently available:

 - mysql: using mysql as backend (configure mysql details in application-mysql.properties)
 - h2: using h2 as backend
 - dev: create admin account if not already exists on startup
 - ldap: if database not populated yet, import data from the seed-data/ldap folder as well as division data from UoA LDAP
 - mimimal: if database not populated yet, import data from teh seed-data/minimal folder
 
So, if you want to develop using a mysql db (that is not populated yet), using seed-data from LDAP, issue:

    mvn clean spring-boot:run -Dspring.profiles.active="mysql,ldap,dev" 

    
### Test using commandline client

*proji* ( https://github.com/UoA-eResearch/proji ) is the a default implementation of a commandline client that can talk
to the project-centre REST interface.

Install using:

    pip install proji
    
Configure authentication in the file $HOME/.proji.conf:

    [default]
    url = http://localhost:8080/api
    username = admin
    token = abcdefg

(sync with the application-xxx.properties files of your server if necessary)

Then use like:

    proji -h
    
For example, list all persons:

    proji list_person
    
Or, only display the fields you are interested in:

    proji -o id,fullName,affiliations list_person
    
    
Or, only display two persons:
 
    proji get_person 913 014 

    

    
### Run tests

#### Integration tests 

    mvn -Dintegration-tests.skip=false clean integration-test
    
### Authentication design

Initial admin user can be configured in *application-[profile].properties* file:

    admin.username=admin
    admin.password=abcdefg


TODO: details 

### Schema notes

#### Identity

Every Person can be associated with one or multiple Identities. Identities are usually per service. The internal *project-centre* user of a Person
is also modeled as Identity.

#### Division

Compared to v1 of the project database, we removed the institutionId, departmentId & divisionId fields from the Person object and replaced it with
a 'affiliation' map which contains the id of the division as key and the id of the role as value. Every Division contains its direct parent, it's 
top-level Division (for example a faculty), and its institutionId ( modeled using advice from: http://novyden.blogspot.co.nz/2008/01/managing-hierarchical-data-tree-in.html )

#### Example sql queries

Example sql queries can be found under develop/example_sql_queries

### Json(de)serialization

We have a few very simple objects (like PersonStatus, etc..) that basically only contain an id and a unique name/code (in order
 to be able to change the name/code without having to change it in all the references too). If those objects are members of 
 other classes we don't serialize the ids, but only the code/names. That makes for a much nicer REST API user experience.
 If an object like this is created/uploaded, the user can choose between the 'internal' db id (if she knows it), or the code.
 
 Persons are deserialized with the affiliations inlined as a map with the (human readable) division codes as keys, and the (again,
 human readable) role names as a value). As stated before, when creating a Person object, either ids or code/names can be used
 and will be properly de-serialized at the backend.
 
#### Person

##### Notice

For the input format, you can use both the division/divisionalRole code or internal id. The code is recommended for readability, also, it's less likely to get it wrong.

##### Return format (short):

    {
      "affiliations": [
        {
          "division": "UOFAK", 
          "role": "Unknown"
        }
      ], 
      "email": "sdfasdfsdf@bbsfsdfsd.de", 
      "endDate": null, 
      "fullName": "fullnametest test", 
      "id": 934, 
      "lastModified": null, 
      "notes": null, 
      "phone": "12341234", 
      "pictureUrl": null, 
      "preferredName": null, 
      "startDate": "2015-01-01", 
      "status": null
    }
    
##### Accepted input formats (minimal required details for creation):

    {
       "email": "em@i.l", 
       "fullName": "Full Name", 
       "phone": "12341234", 
       "startDate":"2015-01-01", 
       "affiliations":
           [ 
              {"division":"UOFAK", 
               "role":"Unknown"}
           ]
    }
    
Or (shorter affiliation format):

    {
        "email": "em@i.l", 
         "fullName": "Full Name", 
         "phone": "12341234", 
         "startDate":"2015-01-01", 
         "affiliations":
             {"UOFAK":"Unknown"}
    }
    
    
    
#### Project

##### Notice

For the input format, you can use both the division/status/type code or internal id. The code is recommended for readability, also, it's less likely to mix it up and get it wrong.

##### Return format (short):

    {
      "code": "proj_code_1", 
      "description": "blah blah blah", 
      "divisions": ["Division 1"], 
      "endDate": null, 
      "id": 932, 
      "nextReviewDate": "2016-01-01", 
      "notes": null, 
      "requirements": null, 
      "startDate": "2015-10-10", 
      "status": "Closed", 
      "title": "temptitle", 
      "todo": null, 
      "type": "Collaborator"
    }
    
    
##### Input format (minimal required details for creation):

    {
       "code":"proj_code_1", 
       "description":"blah blah blah", 
       "title":"Project title", 
       "startDate":"2015-10-10", 
       "status":"Pending", 
       "type":"Collaborator"
    }



### Seed data

For development and testing, *project-centre* can be pre-loaded with sets of seed data using json files (check out seed-data/minimal for an example).
The data is loaded using a CommandLineRunner class (SeedDataImporter), which supports also loading only certain levels of data:

    mvn spring-boot:run -Dspring.profiles.active="dev" -Drun.arguments="seed-level=2,seed-location=seed-data/minimal"
    
Seed levels:

 - 1
     - DivisionalRole
     - Faculty
     - Institution
     - Kpi
     - KpiCategory
     - PersonRole
     - PersonStatus
     - ProjectActionType
     - ProjectStatus
     - ProjectType
     - ResearchOutputType
 - 2
     - Division
 - 3 
     - Person
 - 3
     - PersonProperty
 - 4
     - Project
 - 5
     - ProjectProperty
     - ProjectAction
     - ProjectKpi
     - ResearchOutput
     - ExternalReference



