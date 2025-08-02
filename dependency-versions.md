# 📋 Standard Dependency Versions Reference

This document outlines the **latest approved dependency versions** used across projects in the `backend-labs` repository. All new projects or updated builds should follow these versions unless explicitly required otherwise.

## ✅ Current Approved Versions

| Dependency       | Version     | Notes                          | Last Updated |
|------------------|-------------|--------------------------------|--------------|
| Java             | 21          | Standardize across all builds  | 2025-08-01   |
| Gradle Wrapper   | 8.5         | Run `./gradlew wrapper --gradle-version 8.5` | 2025-08-01   |
| Spring Boot      | 3.5.4       | Use with Spring projects       | 2025-08-01   |
| JUnit            | 5.10.x      | Prefer Jupiter engine          | 2025-08-01   |
| Testcontainers   | 1.19.x      | For integration testing        | 2025-08-01   |

---

## 🛠️ How to Apply

When creating or updating any service under `backend-labs/`:

1. **Java Version:** Ensure your `build.gradle` or `.toolchain` uses Java 17.
2. **Gradle Wrapper:** Regenerate with  
   ```bash
   ./gradlew wrapper --gradle-version 8.5
   ```
3. **Spring Boot Projects:** Update spring-boot-starter-parent or Gradle plugin to 3.2.4.
4. **Testing:** Ensure JUnit 5 is used. Remove any older JUnit 4/3 references.
5. **Integration Tests:** Use Testcontainers 1.19.x when needed.

## 🚀 Tip
**You can quickly verify versions across services using:**
```bash
  grep -R 'spring-boot' backend-labs/
  grep -R 'junit' backend-labs/
  grep -R 'testcontainers' backend-labs/
```
