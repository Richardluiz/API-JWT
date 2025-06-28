# Etapa 1: Build com Maven (build stage)
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia todo o projeto para /app no container
COPY . .

# Executa o build do Maven (skip testes para agilizar)
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final para executar o jar
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copia o jar gerado na etapa de build para o container final
COPY --from=builder /app/target/apijwt-0.0.1-SNAPSHOT.jar app.jar

# Expondo a porta 8080 para a aplicação
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
