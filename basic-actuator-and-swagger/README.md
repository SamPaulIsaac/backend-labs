# 📊 Basic Actuator & Swagger Application

A minimal Spring Boot application that demonstrates the integration of **Spring Boot Actuator** and **Swagger/OpenAPI** for observability and API documentation.

---

## ✅ Features

- Sample REST endpoint using `@RestController`
- Swagger 3 (OpenAPI) configuration for API docs
- Spring Boot Actuator enabled for health and metrics
- Lightweight, easy-to-extend foundation

---

## 📁 Endpoints

### 🔹 Sample API

| Method | Path              | Description              |
|--------|-------------------|--------------------------|
| GET    | `/api/sample`     | Returns sample response  |

### 🔹 Swagger UI

- Available at: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### 🔹 Actuator Endpoints

| Path              | Description              |
|-------------------|--------------------------|
| `/actuator/health` | App health status         |
| `/actuator/info`   | Application metadata      |
| `/actuator/metrics`| Runtime metrics overview  |

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Boot Actuator
- Springdoc OpenAPI (Swagger)

---

## ▶️ Running the Application

### Using Gradle

```bash
./gradlew bootRun
