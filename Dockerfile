FROM openjdk:17
MAINTAINER apoorvanp
COPY build/libs/marketplace-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]