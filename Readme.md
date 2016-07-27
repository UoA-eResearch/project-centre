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

### Build version using local database

    mvn clean package -Dspring.profiles.active="local"

### Build version using local or prod database

   mvn clean package -Dspring.profiles.active="[local|prod]"
 
### Run using maven using local database

   # configure database connection parameters in src/main/resources/application-[local|prod].properties
   mvn spring-boot:run -Dspring.profiles.active="prod"
    
### Run using java

   mvn clean package
   java -Dspring.profiles.active="[local|prod]" -jar target/projectcentre-[VERSION].jar
    
### Viewing swagger API

   # after running 
   mvn clean package -Dspring.profiles.active="[local|prod]"
   point browser to http://localhost:8080/swagger-ui.html
