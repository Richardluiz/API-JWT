# Etapa 1: Build com Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final com JDK apenas para execução
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/apijwt-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
