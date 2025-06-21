# 📌 API JWT com Spring Boot

API REST com autenticação e autorização baseada em JWT (JSON Web Token), desenvolvida com Spring Boot. Possui endpoints protegidos por papéis (admin/user), documentação com Swagger, monitoramento com Spring Boot Actuator + Prometheus e deploy com Docker.

---

## 🚀 Tecnologias Utilizadas

- Java 17  
- Spring Boot 3.5.3  
- Spring Security  
- Spring Data JPA  
- H2 Database  
- JWT (JSON Web Token)  
- Swagger / OpenAPI  
- Spring Boot Actuator  
- Prometheus  
- Docker  

---

## ⚙️ Pré-requisitos

- Java 17 instalado  
- Maven ou Wrapper (`mvnw`)  
- Docker instalado  

---

## 📦 Como compilar o projeto

```bash
./mvnw clean package
```

> No Windows:  
> `mvnw.cmd clean package`

Isso gera o `.jar` em `target/`.

---

## 🐳 Rodando com Docker

### 1. Crie o `Dockerfile` na raiz do projeto:

```Dockerfile
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### 2. Build da imagem Docker:

```bash
docker build -t apijwt .
```

### 3. Execute o container:

```bash
docker run -p 8080:8080 apijwt
```

Acesse a aplicação em:  
👉 `http://localhost:8080`

---

## 📑 Principais Endpoints

| Método | Endpoint             | Descrição                         | Acesso     |
|--------|----------------------|-----------------------------------|------------|
| POST   | `/auth/login`        | Autentica e retorna um JWT        | Público    |
| POST   | `/auth/register`     | Cadastra novo usuário             | Público    |
| GET    | `/auth/users`        | Lista usuários                    | ADMIN      |
| DELETE | `/auth/users/{id}`   | Remove um usuário pelo ID         | ADMIN      |
| GET    | `/protected`         | Endpoint protegido para testes    | USER/ADMIN |
| GET    | `/actuator/health`   | Health Check                      | Público    |
| GET    | `/actuator/prometheus` | Métricas Prometheus              | Público    |

---

## 🔐 Login no Swagger

1. Acesse: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
2. Faça um `POST` em `/auth/login` com:

```json
{
  "username": "admin",
  "password": "123"
}
```

3. Copie o token JWT retornado.
4. No Swagger, clique em "Authorize" e insira:

```
Bearer SEU_TOKEN
```

---

## 🛠️ Acesso ao H2 Database

- URL: `http://localhost:8080/h2-console`  
- JDBC URL: `jdbc:h2:file:./src/main/resources/db/bancoDeDados`  
- Usuário: `sa`  
- Senha: (vazio)

---

## 🧪 Usuários Criados (via DataInitializer)

| Username | Senha | Papel      |
|----------|-------|------------|
| admin    | 123   | ROLE_ADMIN |
| user     | 123   | ROLE_USER  |

---

## 📊 Monitoramento com Actuator & Prometheus

- Verificar status da API:  
  `http://localhost:8080/actuator/health`

- Verificar métricas Prometheus:  
  `http://localhost:8080/actuator/prometheus`

---

## 📝 Autor

- **Richard Luiz**  
- Projeto acadêmico com foco em autenticação JWT, segurança com Spring Security, métricas com Prometheus e deploy com Docker.

---
