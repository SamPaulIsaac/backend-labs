# 🧪 Repository Testing with `@SpringBootTest`

This module demonstrates how to test Spring Data JPA repositories in a realistic Spring Boot context using `@SpringBootTest`. It includes full wiring of the Spring container and real database interaction.

---

## 🎯 Purpose

- Validate repository logic within a real Spring container
- Interact with an actual database schema
- Leverage Liquibase for schema consistency
- Ensure persistence layer correctness in isolation

---


---

## 🧪 Test Strategy

- Uses `@SpringBootTest` for full application context loading
- Repository logic is tested against a real MySQL instance
- `@ActiveProfiles("repositoryTest")` activates custom DB and Liquibase setup
- Liquibase migrations ensure schema setup before test execution

---

## 🛠 Tech Stack

- **Spring Boot 3.3.3**
- **Java 17**
- **JUnit 5**
- **Spring Data JPA**
- **Liquibase**
- **MySQL**
- **Gradle Source Set: `repositoryTest`**

---

## ⚙️ Gradle Configuration

Custom `repositoryTest` source set allows for separate lifecycle and dependency management of repository-level tests.

Run the tests with:

```bash
./gradlew repositoryTest

