FROM openjdk:8-jre-alpine AS BUILD

MAINTAINER Andrea

VOLUME /tmp

#The project JAR file is added to the container as app.jar and then #executed in the ENTRYPOINT
ADD target/ConfigurationServer-0.0.1-SNAPSHOT.jar ConfigurationServer-0.0.1-docker.jar

#ARG JAR_FILE

#RUN command to “touch” the jar file so that it has a file #modification time
#RUN bash -c ‘touch /app.jar’
#RUN echo hello > /hello
#RUN echo world >> /hello
#RUN touch remove_me /remove_me
#RUN rm /remove_me
#RUN apk add --no-cache python g++ make
#RUN yarn install --production

#ENV HELLO=world

#WORKDIR /app

#COPY target/Template-0.0.1-SNAPSHOT.jar target/Template-0.0.1-SNAPSHOT.jar
#COPY target/lib target/lib

#on which port our application is listing
EXPOSE 8888

#CMD ["node", "src/index.js"]

#USER nonroot:nonroot

# To reduce Tomcat startup time we added a system property pointing # to “/dev/urandom” as a source of entropy.
#ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8002", "-jar", "consumer-docker.jar"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom" ,"-jar","target/SSM_Voucher-1.0.0.jar"]
ENTRYPOINT ["java", "-jar", "ConfigurationServer-0.0.1-docker.jar"]
