# 🧾 File Logging App (Spring Boot)

A minimal Spring Boot application to demonstrate how to configure file-based logging with size-based rotation.

---

## 📌 Purpose

This app is a reference setup to enable **log file generation**, with control over:

- Log file name and location
- Max log file size before rotation
- Logging output format (optional)

---

## ⚙️ Configuration

These settings are defined in `application.properties`:

```properties
# Log file name (relative or absolute path)
logging.file.name=log_file.log

# Maximum size of the log file before rolling over
logging.file.max-size=1MB
