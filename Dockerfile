FROM public.ecr.aws/amazoncorretto/amazoncorretto:11
WORKDIR /app
COPY target/oauth-server.jar /app
EXPOSE 9000
ENTRYPOINT ["java","-jar","oauth-server.jar"]
