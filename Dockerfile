FROM openjdk:17-jdk-alpine
LABEL maintainer="sazzad.com"
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]