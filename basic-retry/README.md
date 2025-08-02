# 🔁 Retry Mechanism Demo

A Spring Boot application showcasing how to implement retry logic using `RetryTemplate`. Useful for simulating retries on transient failures when calling external APIs.

---

## 🚀 Features

- Uses Spring Retry's `RetryTemplate` for retrying operations
- Configurable retry logic (attempts, delays, etc.)
- Simple REST endpoint to trigger retry logic
- Logs retry attempts and timestamps

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot
- Spring Retry
- Lombok
- Gradle

---

## ▶️ Running the Application

```bash
./gradlew bootRun
