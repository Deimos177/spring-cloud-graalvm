FROM openjdk:17-alpine as build

ENV LANG pt_BR.UTF-8
ENV LANGUAGE pt_BR:pt:en

WORKDIR /usr/lib

RUN apk update && \
    apk add wget
RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz
RUN tar -xvf apache-maven-3.9.6-bin.tar.gz

WORKDIR /app

RUN export MAVEN_HOME=/usr/lib/apache-maven-3.9.6
RUN export PATH=$PATH:$MAVEN_HOME

COPY . .

RUN /usr/lib/apache-maven-3.9.6/bin/mvn clean package -e

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]