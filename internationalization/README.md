# 🌍 Spring Boot Internationalization (i18n) Demo

A simple Spring Boot application demonstrating how to implement internationalization using `MessageSource`. The app returns localized greetings based on the request's `Accept-Language` header or default locale.

---

## ✅ Features

- Uses Spring Boot's built-in support for `MessageSource`
- Retrieves locale-specific messages from `.properties` files
- Locale can be passed via request or resolved automatically
- Demonstrates usage via a simple `/greeting` REST endpoint

---

## 🔹 Sample Messages

```properties
# messages.properties (default - English)
greeting=Hello!
farewell=Goodbye!

# messages_es.properties (Spanish)
greeting=¡Hola!
farewell=¡Adiós!
```

## ▶️ Running the Application
```bash
  ./gradlew bootRun
```
