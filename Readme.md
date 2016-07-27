# Project-Centre

Service to manage research projects for the Centre for eResearch at the University of Auckland

## Requirements

 - Java 8
 - Maven (version >= 3)
 - MySQL server to connect to

## Big TODOs

 - write unit & integration tests
 
## Development

### Getting started

    git clone https://github.com/UoA-eResearch/project-centre.git
    cd project-centre
    cp src/main/resources/application.properties.sample src/main/resources/application-local.properties
    # adjust database connection parameters in src/main/resources/application-local.properties

### Build version 

    mvn clean package -Dspring.profiles.active="local"

### Run using maven 

    mvn spring-boot:run -Dspring.profiles.active="local"
    
### Run using java

    java -Dspring.profiles.active="local" -jar target/projectcentre-[VERSION].jar
    
### Viewing swagger API

Point browser to http://localhost:8080/swagger-ui.html after starting the app as described above
