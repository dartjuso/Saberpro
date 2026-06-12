# ETAPA 1: Construcción (Aquí compilamos)
FROM gradle:7.6-jdk17 AS build
WORKDIR /app
# Copiamos todo lo de la carpeta saber-pro-web
COPY . .
# Compilamos (esto crea la carpeta build dentro de Render automáticamente)
RUN ./gradlew build -x test

# ETAPA 2: Ejecución (Aquí solo copiamos el .jar resultante)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copiamos el archivo que se generó en la etapa de construcción
# Cambia 'saber-pro-web-0.0.1-SNAPSHOT.jar' por el nombre real de tu jar si es diferente
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8103
ENTRYPOINT ["java", "-jar", "app.jar"]
