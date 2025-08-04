# CompletableFuture Orchestration Demo

This service demonstrates orchestration patterns using Java's `CompletableFuture` in a Spring Boot application. It simulates a customer profile fetching pipeline by combining, composing, and coordinating async tasks.

## ✅ Features
- thenCombine (Phase A)
- thenCompose (Phase B)
- allOf & anyOf (Phase C)
- thenAcceptBoth & runAfterBoth (Phase D)
- Exception Handling: exceptionally, handle, whenComplete (Phase E)

## 📦 Technologies
- Java 17+
- Spring Boot 3+
- Lombok
- SLF4J for structured logging

## 📂 Package Structure
| Phase   | Description                   | Endpoint                                                                            |
| ------- | ----------------------------- | ----------------------------------------------------------------------------------- |
| Phase A | thenCombine                   | GET /api/customers/phase-a                                                          |
| Phase B | thenCompose                   | GET /api/customers/phase-b                                                          |
| Phase C | allOf / anyOf                 | GET /api/customers/phase-c/all-of<br>GET /api/customers/phase-c/any-of              |
| Phase D | thenAcceptBoth / runAfterBoth | GET /api/customers/phase-d/accept-both<br>GET /api/customers/phase-d/run-after-both |
| Phase E | Exception Handling            | GET /api/customers/phase-e                                                          |

## 🧪 Sample Curl
```bash
curl -X GET http://localhost:8080/api/customers/phase-a
```
## 📎 Endpoint Reference
- All curl examples are located in:
```bash
/home/bereka/sampaulisaac-home/sampaulisaac-github-repo/backend-labs/async-patterns/completablefuture-orchestration/endpoints/test_endpoints.txt
```

## 🚀 Running the App
```bash
./gradlew bootRun
```
