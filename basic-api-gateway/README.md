# Basic API Gateway System

This repository contains a minimal Spring Boot microservices setup demonstrating service registration, gateway routing, and basic service functionality. It consists of the following components:

## 📘 Overview

- **API Gateway (`api-gateway`)**
  - Routes and filters requests to backend services.
  - Integrated with **Eureka Service Registry** for service discovery.
  - Implements **circuit breaker** fallback for resilience.

- **Service Registry (`service-registry`)**
  - Powered by **Spring Cloud Eureka Server**.
  - Centralized registry for service instances.

- **User Service (`user-service`)**
  - REST API to create and retrieve user entities.
  - Uses in-memory H2 database.
  - Registers with Eureka and exposes endpoints via the API Gateway.

- **Product Service (`product-service`)**
  - REST API to create and retrieve product entities.
  - Uses in-memory H2 database.
  - Registers with Eureka and exposes endpoints via the API Gateway.

---

## 🌐 Exposed Endpoints

| Service         | Endpoint                     | Description                  |
|-----------------|------------------------------|------------------------------|
| API Gateway     | `/api/user/**`               | Routes to User Service       |
|                 | `/api/product/**`            | Routes to Product Service    |
| User Service    | `/api/user/create` (POST)    | Create a new user            |
|                 | `/api/user/all` (GET)        | Fetch all users              |
| Product Service | `/api/product/create` (POST) | Create a new product         |
|                 | `/api/product/all` (GET)     | Fetch all products           |

---

## 🧩 Tech Stack

- **Spring Boot**
- **Spring Cloud Gateway**
- **Spring Cloud Eureka**
- **Spring Data JPA**
- **H2 In-Memory Database**
- **Lombok**
- **SLF4J (logging)**

---

## 📝 Notes

- All services are independently runnable Spring Boot applications.
- Each service uses a local port and registers itself with Eureka at runtime.
- Gateway performs service discovery and routing dynamically based on Eureka registration.

---

## 🏁 Quick Start

Each service can be started individually using:

```bash
./gradlew bootRun
