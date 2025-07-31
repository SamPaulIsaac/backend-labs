# 🔁 Integration Testing Module

This module demonstrates how to write full-stack integration tests in a Spring Boot application, including real database interactions and HTTP endpoint verification.

---

## 🎯 Purpose

- Validate the application holistically through HTTP-level tests
- Confirm actual controller–service–repository integration
- Use a real MySQL database and Liquibase migrations for a production-like setup

---

## 🧪 Test Strategy

- Uses `@SpringBootTest` with a real HTTP layer (`RANDOM_PORT`)
- Verifies the `Cricketer` REST API end-to-end
- `TestRestTemplate` is used for simulating HTTP requests
- Relies on a dedicated test database and Liquibase changelogs

---

## 🛠 Tech Stack

- **Spring Boot 3.3.3**
- **Java 17**
- **JUnit 5**
- **TestRestTemplate**
- **Liquibase**
- **MySQL**
- **Gradle Custom SourceSet for Integration Testing**

---

## ⚙️ Gradle Configuration

The module defines a custom `integrationTest` source set with its own dependencies and lifecycle tasks.

Run integration tests:

```bash
./gradlew integrationTest
