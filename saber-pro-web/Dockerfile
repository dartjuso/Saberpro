# Cambiamos openjdk:17 por esta, que es la versión oficial mantenida
FROM eclipse-temurin:17-jre-alpine

# OJO: Como vi que usas Gradle, tu archivo no está en 'target', está en 'build/libs'
# Ajusta esta línea según tu caso:
COPY build/libs/*.jar app.jar

EXPOSE 8103
ENTRYPOINT ["java", "-jar", "app.jar"]
