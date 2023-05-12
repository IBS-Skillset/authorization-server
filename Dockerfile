FROM public.ecr.aws/amazoncorretto/amazoncorretto:11
WORKDIR /app
COPY target/authorization-server.jar /app
EXPOSE 9000
ENTRYPOINT ["java","-jar","authorization-server.jar"]
