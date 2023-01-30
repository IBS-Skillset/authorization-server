FROM amazoncorretto:11-alpine-jdk
MAINTAINER happystays
COPY target/oauth-server-1.0.jar oauth-docker-1.0.jar
ENTRYPOINT ["java","-jar","/oauth-docker-1.0.jar"]