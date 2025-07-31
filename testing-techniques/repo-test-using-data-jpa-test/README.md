# 🧪 Repository Testing with `@DataJpaTest`

This service illustrates how to test JPA repositories in isolation using `@DataJpaTest`, ensuring fast, focused validation of repository behavior against a real MySQL database.

---

## 🎯 Purpose

- Perform fast, slice-based persistence tests
- Test repository logic without loading the entire Spring context
- Use a dedicated MySQL schema with Liquibase-managed schema setup

---

## 🧪 Test Strategy

- Uses `@DataJpaTest` for loading only JPA-related components
- `@AutoConfigureTestDatabase(replace = NONE)` ensures tests run against a real database (not H2)
- Repository is tested in isolation from services or controllers
- Liquibase initializes the schema before tests run

---

## 🛠 Tech Stack

- **Spring Boot 3.3.3**
- **Java 17**
- **JUnit 5**
- **Spring Data JPA**
- **Liquibase**
- **MySQL**
- **Gradle with custom source sets**

---

## ⚙️ Gradle Configuration

Defines a separate `repositoryTest` source set for modular execution.

Run the tests with:

```bash
./gradlew repositoryTest


