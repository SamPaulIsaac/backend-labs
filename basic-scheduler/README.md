# 🕒 Basic Scheduler

A Spring Boot application that demonstrates scheduling capabilities using `@Scheduled`. Useful for understanding how `fixedRate`, `fixedDelay`, and `cron` expressions work in real-time.

---

## ✅ Features

- Simulates generating and expiring OTPs
- Uses three scheduling strategies:
  - `@Scheduled(fixedRate)` – run at regular intervals
  - `@Scheduled(fixedDelay)` – run after a fixed delay from previous completion
  - `@Scheduled(cron)` – run at specific time-based schedules
- Thread-safe `ConcurrentHashMap` used for OTP storage
- SLF4J-based structured logging for execution tracking

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot
- Spring Scheduling
- SLF4J (Logback)
- Gradle

---

## ▶️ Running the Application

```bash
./gradlew bootRun
