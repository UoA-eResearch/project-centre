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

### JsonDeserialization

We have a few very simple objects (like PersonStatus, etc..) that basically only contain an id and a unique name/code (in order
 to be able to change the name/code without having to change it in all the references too). If those objects are members of 
 other classes we don't serialize the ids, but only the code/names. That makes for a much nicer REST API user experience.
 If an object like this is created/uploaded, the user can choose between the 'internal' db id (if she knows it), or the code.
 
 Persons are deserialized with the affiliations inlined as a map with the (human readable) division codes as keys, and the (again,
 human readable) role names as a value). As stated before, when creating a Person object, either ids or code/names can be used
 and will be properly de-serialized at the backend.

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



