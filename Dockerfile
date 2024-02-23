FROM container-registry-saopaulo.oracle.com/java/openjdk:21-oraclelinux8 as build

WORKDIR /usr/lib

RUN yum update && \
    yum install wget -y && \
    wget https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz && \
    tar -xvf apache-maven-3.9.6-bin.tar.gz

ENV MAVEN_HOME /usr/lib/apache-maven-3.9.6
ENV PATH $PATH:$MAVEN_HOME/bin

WORKDIR /app

COPY . .

RUN mvn clean package

FROM container-registry-saopaulo.oracle.com/java/openjdk:21-oraclelinux8

RUN yum update && \
    yum install -y glibc-langpack-pt && \
    yum clean all

ENV LC_ALL pt_BR.UTF-8

WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]