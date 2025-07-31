# ✅ Multi-part File Upload Service

A Spring Boot application to handle basic multi-part file uploads — primarily CSV file uploads for bulk processing of book data. Clean architecture, layered structure, and DTO-based validation are supported.

---

## 🚀 Features

- Upload books via CSV file (multi-part)
- Process and persist book data
- DTO validation and clean error handling
- Modular service architecture

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- Multipart Resolver
- Lombok
- Gradle

---

## ▶️ Running the Application

Using Gradle:

```bash
./gradlew bootRun
```
## 📫 API Endpoints
  ### 📚 Book Upload
  - POST /books/upload – Accepts a multipart file (CSV) to upload book data

