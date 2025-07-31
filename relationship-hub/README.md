# Relationship Hub

A modular Spring Boot application demonstrating JPA entity relationships: One-to-One, One-to-Many, and Many-to-Many. Built with clean RESTful APIs, H2 in-memory database, and Liquibase for schema versioning.

---

## 📚 Modules & Features

### 🔹 One-to-One: Employee Management
- **Endpoints**:
  - `POST /api/employees/create` – Create a new employee
  - `GET /api/employees/{id}` – Get employee by ID
  - `PUT /api/employees/{id}` – Full update
  - `PATCH /api/employees/{id}` – Partial update
  - `DELETE /api/employees/{id}` – Delete employee

### 🔹 One-to-Many: Project Management
- **Endpoints**:
  - `POST /api/projects/create` – Create a project
  - `PUT /api/projects/{id}` – Full update
  - `PATCH /api/projects/{id}` – Partial update
  - `DELETE /api/projects/{id}` – Delete project
  - `GET /api/projects/all` – List all projects
  - `GET /api/projects/{id}` – Get project by ID

### 🔹 Many-to-Many: Student–Course Enrollment
- **Endpoints**:
  - `POST /api/students` – Create student
  - `GET /api/students/{id}` – Get student by ID
  - `POST /api/courses` – Create course
  - `GET /api/courses/{id}` – Get course by ID
  - `POST /api/enrollments` – Enroll student into course

---

## 🛠 Tech Stack

- **Spring Boot 3.5.4**
- **Java 17**
- **Spring Data JPA**
- **H2 In-Memory Database**
- **Liquibase** (Schema versioning)
- **Lombok** (Boilerplate reduction)
- **JUnit 5** (Testing)

---

## ▶️ Running the Application

```bash
./gradlew bootRun
