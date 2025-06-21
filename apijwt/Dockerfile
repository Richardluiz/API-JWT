# Usar imagem base com JDK 21 da Eclipse Temurin (OpenJDK)
FROM eclipse-temurin:21-jdk-jammy

# Definir diretório de trabalho dentro do container
WORKDIR /app

# Copiar o JAR gerado pelo Maven para dentro do container
COPY target/apijwt-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta que o Spring Boot usa (por padrão 8080)
EXPOSE 8080

# Comando para rodar a aplicação Java
ENTRYPOINT ["java", "-jar", "app.jar"]