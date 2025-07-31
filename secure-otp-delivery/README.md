# Secure OTP Delivery Service

A lightweight, secure, and scalable microservice for sending and verifying OTPs (One-Time Passwords) via SMS. Backed by Redis for state management and integrated with Twilio for SMS delivery.

---

## Features

- Send and verify numeric OTPs via SMS
- OTP rate limiting, retry tracking, and attempt blocking
- Redis-backed real-time metrics tracking
- Twilio integration for SMS transmission
- Jakarta Bean Validation for input sanitization
- Structured logging with SLF4J
- Modular design following clean code principles

---

## Technology Stack

| Layer         | Technology                     |
|---------------|---------------------------------|
| Language       | Java 17                        |
| Framework      | Spring Boot 3                  |
| Messaging      | Twilio SMS API                 |
| Data Store     | Redis (via Spring Data Redis)  |
| Validation     | Jakarta Validation (JSR-380)   |
| Logging        | SLF4J + Spring Boot Logging    |
| Build Tool     | Gradle                         |

---

## Setup Instructions

### Prerequisites

- Java 17 or later
- Redis server (local or Docker-based)
- Twilio account with valid SMS credentials
- Gradle

---

### 1. Clone the Repository

```bash
git clone https://github.com/your-org/secure-otp-delivery.git
cd secure-otp-delivery
```
### 2. Configure Twilio Credentials
**Update the `application.yml` configuration with your Twilio credentials:**

```yaml
twilio:
  accountSid: YOUR_TWILIO_ACCOUNT_SID
  authToken: YOUR_TWILIO_AUTH_TOKEN
  fromNumber: YOUR_TWILIO_PHONE_NUMBER
```
### 3. Start Redis
**Local Installation**
```bash
redis-server
```
### 4. Build and Run the Application
```bash
./gradlew clean build
./gradlew bootRun
