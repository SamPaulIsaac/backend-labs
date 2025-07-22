# Alarm Application API Documentation

## Available API

---

## ` 1. Create Alarm`

- **URL:** `/api/alarms`
- **Method:** POST

**Request Body:**

```json
{
  "title": "Morning Alarm",
  "time": "2025-05-16T07:00:00",
  "isRecurring": true,
  "isActive": true,
  "recurrencePattern": "DAILY",
  "ringtone": "default",
  "createdBy": "user123",
  "updatedBy": "user123",
  "createdAt": "2025-05-16T06:00:00",
  "updatedAt": "2025-05-16T06:00:00"
}
```

---

## `2. Get All Alarms`

- **URL:** /api/alarms/all
- **Method:** GET

---a

## `3. Get All Active Alarms`

- **URL:** /api/alarms/active
- **Method:**  GET

---

## `4. Update Alarm`

- **URL:** /api/alarms/{id}
- **Method:**  PUT

**Request Body:**

```json
{
  "title": "Updated Alarm",
  "time": "2025-05-16T08:00:00",
  "isRecurring": false,
  "isActive": true,
  "recurrencePattern": "WEEKLY",
  "ringtone": "bell",
  "updatedBy": "user123",
  "updatedAt": "2025-05-16T07:30:00"
}
```

---

## `5. Delete Alarm`

- **URL:** /api/alarms/{id}
- **METHOD:** DELETE

---

## Notes

> time, createdAt, and updatedAt fields use ISO 8601 format.

> recurrencePattern can be one of: DAILY, WEEKLY, MONTHLY.

> isActive controls whether the alarm is active or disabled.

---