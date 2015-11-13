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
 

### Getting started

    git clone xxx
    cd project-centre
    # we use the 'dev' profile for development
    mvn  spring-boot:run  -Dspring.profiles.active="dev"
    
### Run tests

#### Integration tests 

    mvn -Dintegration-tests.skip=false clean integration-test
    
### Authentication design

TODO: details

### Schema notes

#### Identity

Every Person can be associated with one or multiple Identities. Identities are usually per service. The internal *project-centre* user of a Person
is also modeled as Identity.

#### Division

Compared to v1 of the project database, we removed the institutionId, departmentId & divisionId fields from the Person object and replaced it with
a 'affiliation' map which contains the id of the division as key and the id of the role as value. Every Division contains its direct parent, it's 
top-level Division (for example a faculty), and its institutionId ( modeled using advice from: http://novyden.blogspot.co.nz/2008/01/managing-hierarchical-data-tree-in.html )

### Seed data

For development and testing, *project-centre* can be pre-loaded with sets of seed data using json files (check out seed-data/minimal for an example).
The data is loaded using a CommandLineRunner class (SeedDataImporter), which supports also loading only certain levels of data:

    mvn spring-boot:run -Dspring.profiles.active="dev" -Drun.arguments="seed-level=2,seed-location=seed-data/minimal"
    
Seed levels:

#### 1

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
 
#### 1

 - Division
 
#### 2

 - Person
 
#### 3

 - PersonProperty
 
#### 4

 - Project
 
#### 5

 - ProjectProperty
 - ProjectAction
 - ProjectKpi
 - ResearchOutput
 - ExternalReference



