# ETAPA 1: BUILD
FROM alpine:latest as build
RUN apk update && apk add openjdk17
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

# ETAPA 2: RUNTIME
FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8080
COPY --from=build ./build/libs/DnaAnalyzer-0.0.1-SNAPSHOT.jar ./app.jar

# CAMBIO CLAVE: Usamos formato shell (sin corchetes) para evitar errores de parsing
ENTRYPOINT java -jar app.jar