# 🧪 Repository Testing with Testcontainers

This module demonstrates how to write real-world repository tests using [Testcontainers](https://www.testcontainers.org/) with a live MySQL database, all in an isolated and repeatable environment.

---

## 🎯 Purpose

- Perform true database integration tests using real MySQL in Docker
- Isolate repository-layer behavior from the rest of the application
- Avoid mocking and instead use production-like data access conditions

---

## 🚀 Features

- Uses a dedicated Gradle `repositoryTest` source set
- Automatically starts and stops a Dockerized MySQL container for each test session
- Verifies repository methods (e.g., `findAll`, `saveAll`) using real data

---

## 🧱 Technology Stack

- **Spring Boot 3.3.3**
- **Java 17**
- **Spring Data JPA**
- **JUnit 5**
- **Testcontainers: MySQL + JUnit Jupiter**
- **MySQL 8 (Dockerized)**
- **Gradle source sets for test isolation**

---

## ✅ Gradle Task
Run repository tests:
```bash
./gradlew repositoryTest
```

## 📌 Notes
- Isolated Environment: Every test runs against a clean containerized DB
- No Local MySQL Setup Needed: Docker handles the database
- Profile-Aware: Uses @ActiveProfiles("repositoryTest") for configuration scoping

