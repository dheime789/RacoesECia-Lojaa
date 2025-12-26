# Etapa 1: Construir o site (Usando Maven + Eclipse Temurin)
FROM maven:3.9-eclipse-temurin-17-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Rodar o site (Usando Eclipse Temurin leve)
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]