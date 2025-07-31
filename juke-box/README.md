# ✅ Juke Box App

A Spring Boot–based music management service that allows users to manage musicians and music albums. The app supports creating, updating, and retrieving musical data with domain-level validations and clean architecture.

---

## 🚀 Features

- Musician and album creation/update with validation
- Duplicate detection for musicians
- Album search by:
  - Date of release (chronologically sorted)
  - Associated musician
- Clean separation of controller, service, and repository layers
- RESTful API design

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- Hibernate Validator
- Gradle
- Lombok

---

## ▶️ Running the Application

Using Gradle:

```bash
./gradlew bootRun
```
### API Highlights
 🎤 Musicians
  - POST /musicians – Create or update musician
  - GET /musicians/{id} – Retrieve musician by ID

 💿 Albums
  - POST /albums – Create or update album
  - GET /albums – Retrieve albums (filter by musician or date)

