# This dockerfile does not depend on any local files, it will pull down all requirements from Github.

# pull base image
FROM debian:jessie

# add webupd8 repository
RUN \
    echo "===> add webupd8 repository..."  && \
    echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee /etc/apt/sources.list.d/webupd8team-java.list  && \
    echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee -a /etc/apt/sources.list.d/webupd8team-java.list  && \
    apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886  && \
    apt-get update  && \
    \
    \
    echo "===> install Java"  && \
    echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections  && \
    echo debconf shared/accepted-oracle-license-v1-1 seen true | debconf-set-selections  && \
    DEBIAN_FRONTEND=noninteractive  apt-get install -y --force-yes oracle-java8-installer oracle-java8-set-default git && \
    \
    \
    echo "===> clean up..."  && \
    rm -rf /var/cache/oracle-jdk8-installer  && \
    apt-get clean  && \
    rm -rf /var/lib/apt/lists/*

RUN cd /opt

RUN \
    echo "===> install Maven" && \
    cd /opt && \
    wget http://ftp-stud.hs-esslingen.de/pub/Mirrors/ftp.apache.org/dist/maven/maven-3/3.3.3/binaries/apache-maven-3.3.3-bin.tar.gz && \
    tar xvzf apache-maven-3.3.3-bin.tar.gz &&\
    rm apache-maven-3.3.3-bin.tar.gz

RUN \
 echo "===> cloning project-centre repo" && \
 cd /opt && \
 git clone https://github.com/UoA-eResearch/project-centre.git && \
 cd /opt/project-centre && \
 /opt/apache-maven-3.3.3/bin/mvn clean package

# Define commonly used JAVA_HOME variable
#ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

EXPOSE 8080
WORKDIR /opt/project-centre/
CMD ["/opt/apache-maven-3.3.3/bin/mvn", "spring-boot:run", "-Dspring.profiles.active=dev", "-Drun.arguments=/opt/project-centre/seed-location=seed-data/minimal" ]
#CMD ["java", "-Dspring.profiles.active=dev", "-Drun.arguments=seed-location=/opt/project-centre/seed-data/minimal", "-Xms512m", "-Xmx1g", "-jar", "projects_centre.jar"]