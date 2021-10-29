FROM openjdk:8-jre-alpine

MAINTAINER Andrea

ADD target/SpringBootOAuth2OpenIDJWTServer-0.0.1-SNAPSHOT.jar SpringBootOAuth2OpenIDJWTServer-0.0.1-docker.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "SpringBootOAuth2OpenIDJWTServer-0.0.1-docker.jar"]
