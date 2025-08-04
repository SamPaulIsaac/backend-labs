# ⚡ completablefuture-basic

A foundational Spring Boot service demonstrating how to use `CompletableFuture` for asynchronous programming — without relying on Spring’s `@Async` abstraction.

---

## 🔍 Overview

This module serves as a learning sandbox to explore native Java `CompletableFuture` APIs like:

- `supplyAsync`
- `thenApply`, `thenAccept`, `thenRun`
- `thenCombine`, `thenCompose`
- `allOf`, `anyOf`
- `exceptionally`, `handle`, `whenComplete`

---

## ⚙️ Tech Stack

- Java 21
- Spring Boot
- Lombok
- SLF4J (Logging)

---
📂 Endpoint File Location

```bash
/home/bereka/sampaulisaac-home/sampaulisaac-github-repo/backend-labs/async-patterns/completablefuture-basic/endpoints/test_endpoints.txt
```
---
▶️ Run Locally

```bash
./gradlew bootRun
```
