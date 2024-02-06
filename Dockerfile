FROM openjdk:23-slim as build

WORKDIR /usr/lib

RUN apt-get update && \
    apt-get install wget -y && \
    wget https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz && \
    tar -xvf apache-maven-3.9.6-bin.tar.gz

ENV MAVEN_HOME /usr/lib/apache-maven-3.9.6
ENV PATH $PATH:$MAVEN_HOME/bin

WORKDIR /app

COPY . .

RUN mvn clean package -Dmaven.test.skip -e

FROM openjdk:23-slim

ENV LANG pt_BR.utf8

WORKDIR /app

RUN apt-get update && \
    apt-get install curl -y
    
COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]