# ETAPA 1: Construcción (Aquí compilamos)
FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY . .
# Damos permisos al gradlew y compilamos
RUN chmod +x gradlew && ./gradlew build -x test

# ETAPA 2: Ejecución (Aquí solo copiamos el .jar resultante)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copiamos el archivo que se generó en la etapa de construcción
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8103
ENTRYPOINT ["java", "-jar", "app.jar"]
