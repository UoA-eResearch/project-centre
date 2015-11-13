# Project-Centre

A lean and mean service to manage research projects for the Centre for eResearch at the University of Auckland.

## Requirements

 - Java 8
 - maven (version >= 3)

## TODOs

 - more documentation
 - Exception handling
 - create seed data
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
    
### Authentication design

TODO: details

### Init with seed data




