# 🗂️ Spring Cloud Config Repository

This repository acts as a **Git-backed configuration store** for a Spring Cloud Config Server. It contains centralized configuration files for various Spring Boot client applications across multiple environments (e.g., dev, qa, prod).

---

## 🛠️ Usage

This repository is intended to be **cloned by or linked to** a Spring Cloud Config Server via `application.yml` or environment properties:

### Sample Config Server Setup

```yaml
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/your-org/conf-g-cloud-server
          clone-on-start: true
```
### 🔗 Sample Client Access (via Config Server)
 - Assuming your config server is running at http://localhost:8888:

  ## URL	Description
  http://localhost:8888/client-application/dev	=> Loads client-app.yml + client-application-dev.yml
  http://localhost:8888/client-application/qa	=> Loads client-app.yml + client-application-qa.yml
  http://localhost:8888/application/default	=> Loads global application.yml

### ✅ Guidelines
 - File name format: {application-name}-{profile}.yml

