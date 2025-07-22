# 🎵 Juke Box App

A robust Spring Boot application to manage music albums and musicians, offering seamless APIs to create, update, and search music data. Built for scalability and clarity, it supports strong domain validation, clean layering, and extensibility.

---

## 🚀 Features

- ✅ Create or update music albums with validation
- ✅ Create or update musicians with duplicate detection
- 🔍 Search music albums by:
  - Date of release (chronologically sorted)
  - Associated musician
- 🔍 Search musicians by:
  - Associated music album
- 🛡️ Full request validation with proper HTTP error responses
- 🧩 Clean service/controller separation with DTO mapping

---

## 🧠 Tech Stack

- **Framework**: Spring Boot 3+
- **Language**: Java 17+
- **Build Tool**: Maven
- **Database**: H2 / PostgreSQL / MySQL (pluggable)
- **Logging**: SLF4J with Logback
- **Testing**: JUnit + Mockito

---

## ⚙️ Running the App

### Prerequisites

- Java 17+
- Maven 3.8+

### Steps

```bash
# Clone the repository
git clone https://github.com/your-org/juke-box-app.git
cd juke-box-app

# Build the application
mvn clean install

# Run the app
mvn spring-boot:run
