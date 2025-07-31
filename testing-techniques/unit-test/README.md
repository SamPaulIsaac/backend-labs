# 🧪 Unit Testing Module

This module demonstrates the use of unit testing in a Spring Boot application. It includes a simple REST controller and associated service logic to showcase how to structure and test business components independently.

---

## 📌 Overview

- **Domain**: Cricketer (simple CRUD operations)
- **Layered Testing**: Focus on both controller and service layer
- **Testing Tools**: JUnit 5, Mockito, Spring Boot Test

---

## 🛠 Tech Stack

- Java 17  
- Spring Boot 3.3.3  
- Spring Web, Spring Data JPA  
- JUnit 5, Mockito  
- Lombok  
- MySQL (runtime only)

---

## 🧪 Testing

The module includes unit tests to verify:
- Service logic in isolation
- REST endpoints using mocked services

Run tests using:

```bash
./gradlew test
