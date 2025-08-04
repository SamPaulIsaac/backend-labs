# AOP Request Tracker

A Spring Boot application demonstrating how to use Aspect-Oriented Programming (AOP) to audit and log user actions on API endpoints.

## Features

- Custom annotation `@TrackUserAction` to mark methods for audit logging.
- Aspect that logs:
  - Method execution time (`@Around`)
  - Method entry with timestamp and arguments (`@Before`)
  - Successful method return (`@AfterReturning`)
  - Method completion regardless of outcome (`@After`)
- Extensible for integration with Spring Security (for real user info).

## Sample Logs

```bash
[AUDIT-BEGIN] User: sam invoked: AopRequestTrackerController.trigger() with args: [] at: 2025-08-04T23:49:10.123
[AROUND-START] Executing AopRequestTrackerController.trigger()
[AFTER-RETURNING] Method AopRequestTrackerController.trigger() completed successfully.
[AFTER] Method AopRequestTrackerController.trigger() has finished execution.
[AROUND-END] AopRequestTrackerController.trigger() executed in 15ms
```
## How to Run

```bash
./gradlew bootRun
```
