FROM openjdk:17
COPY target/*.jar app.jar
EXPOSE 8103
ENTRYPOINT [ "java", "-jar", "app.jar" ]