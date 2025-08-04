# 🧩 basic-async-pattern

A minimal Spring Boot service demonstrating asynchronous method execution using Spring's `@Async`.

---

## 🔍 Overview

This service showcases how to run background tasks using `@Async`, without blocking the main thread. It simulates a workload using `CompletableFuture` and Spring's async infrastructure.

---

## ⚙️ Tech Stack

- Java 17+
- Spring Boot
- Spring Web
- Spring Context (for `@Async`)
- SLF4J + Lombok

---

## 📂 Endpoint File Location

```bash
/home/bereka/sampaulisaac-home/sampaulisaac-github-repo/backend-labs/async-patterns/basic-async-pattern/endpoints/test_endpoints.txt
```

## ▶️ Run Locally

```bash
./gradlew bootRun
```
