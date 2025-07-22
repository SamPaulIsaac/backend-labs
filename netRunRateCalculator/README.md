# 🏏 Net Run Rate (NRR) Calculator

A Spring Boot application to register cricket teams, input match data, and compute each team’s Net Run Rate (NRR) using the standard cricket formula. Lightweight and ideal for cricket analytics dashboards or simulation tools.

---

## 🚀 Features

- Register new cricket teams
- Submit match-specific data (runs scored/conceded, overs faced/bowled)
- Dynamically compute NRR for each team based on stored data
- Simple RESTful design with clean service-controller separation

---

## 🧮 NRR Formula

 - NRR = (Total Runs Scored / Overs Faced) - (Total Runs Conceded / Overs Bowled)
   
---

## 🛠️ Tech Stack

- Java 8
- Spring Boot
- Lombok
- Maven / Gradle

---

## ▶️ Running the Application

```bash
./gradlew bootRun
# or
mvn spring-boot:run


