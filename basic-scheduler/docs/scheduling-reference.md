
# Spring @Scheduled Annotation Reference

## 1. Fixed Rate

- Executes the task at a fixed interval, **regardless of how long the previous execution took**.
- Example:
  ```
  @Scheduled(fixedRate = 2000)  // Runs every 2 seconds
  ```

## 2. Fixed Delay

- Waits for the previous execution to **complete**, then waits the specified delay before the next execution.
- Example:
  ```
  @Scheduled(fixedDelay = 2000)  // Runs every 2 seconds after previous task completes
  ```

## 3. Cron Expression

- Executes the task according to a specific **time-based pattern**.
- Example:
  ```
  @Scheduled(cron = "0 0 12 * * ?")  // Runs every day at 12:00 PM
  ```

### Cron Expression Format (Spring – 6 fields)

```
 ┌───────────── second (0–59)
 │ ┌───────────── minute (0–59)
 │ │ ┌───────────── hour (0–23)
 │ │ │ ┌───────────── day of month (1–31)
 │ │ │ │ ┌───────────── month (1–12 or JAN–DEC)
 │ │ │ │ │ ┌───────────── day of week (1–7 or SUN–SAT, 1=SUN)
 │ │ │ │ │ │
 *  *  *  *  *  *
```

### Common Cron Examples

| Expression         | Description                     |
|--------------------|---------------------------------|
| `0 0 12 * * ?`     | Every day at 12:00 PM           |
| `0 0/5 * * * ?`    | Every 5 minutes                 |
| `0 0 0 * * MON`    | Every Monday at 12:00 AM        |
| `0 0 8-10 * * ?`   | Every hour from 8 to 10 AM      |
| `0 0 12 1 * ?`     | At 12 PM on the 1st day monthly |
