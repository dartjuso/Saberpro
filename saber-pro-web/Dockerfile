# ETAPA 1: Construcción
FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY . .

# LA SOLUCIÓN: Damos permiso de ejecución al script gradlew
RUN chmod +x gradlew
RUN ./gradlew build -x test

# ETAPA 2: Ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8103
ENTRYPOINT ["java", "-jar", "app.jar"]
