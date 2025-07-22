# 🔗 URL Shortener Service

A lightweight Java Spring Boot microservice that generates and resolves short URLs — similar to bit.ly or tinyurl — using in-memory or persistent storage.

---

## 🚀 Features

- Generate short URLs for long input links
- Redirect short URLs to original destinations
- Optional expiration support
- Basic analytics/logging ready for extension

---

## 🛠️ Tech Stack

- **Java 8** (or Java 17+ if modernized)
- **Spring Boot 2.x / 3.x**
- **Gradle / Maven**
- Optional: **H2 / Redis / PostgreSQL** for persistence

---

## ▶️ Running the Application

### Using Gradle

```bash
./gradlew bootRun
