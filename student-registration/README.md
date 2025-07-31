# 🎓 Student Registration System

A multi-service Java backend that handles student registration and asynchronous email notifications using RabbitMQ.

---

## 🚀 Features

- Register students and courses
- Enroll students in courses
- Send registration confirmation emails asynchronously via RabbitMQ

---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.x
- MySQL + Hibernate + Liquibase
- RabbitMQ (AMQP)
- JavaMailSender (SMTP)
- Maven

---

## ▶️ Running the Application

### 🔧 Prerequisites

- Java 17+
- MySQL running locally
- RabbitMQ running locally
- Gmail App Password (for email service)

### 📂 Services

#### 1. Student Registration Service

```bash
cd registration-service
./gradlew bootRun
```

#### 2. Email Notification Service
```bash
cd email-service
./gradlew bootRun
```

