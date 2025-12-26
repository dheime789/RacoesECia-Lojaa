# Etapa 1: Construir o site
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Rodar o site
FROM openjdk:17-jdk-slim
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]