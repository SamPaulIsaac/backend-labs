# Customer Batch Processor

A fault-tolerant Spring Batch application that imports customer data from a CSV file, performs validation, and persists valid records into a database. Invalid records are captured and logged for review.

## Features

- **Spring Batch Integration**: Chunk-oriented batch job for processing customer records.
- **CSV Reader**: Parses customer data from `customers.csv`.
- **Custom Processor**: Applies validation logic and throws exceptions for invalid data.
- **Skip Listener**: Captures and logs failed records during processing or writing.
- **Job Execution Listener**: Handles before/after job events and exports failed records to a CSV file if the job fails.
- **Transactional Support**: Ensures rollback of chunk on failures, with optional skip handling.
- **Fault Tolerance**: Skips up to 5 records per run using `ValidationException` as a known failure.

## Tech Stack

- Java 21
- Spring Boot
- Spring Batch
- Spring Data JPA
- H2 Database (In-memory)
- Lombok
- Jackson (ObjectMapper)
- SLF4J / Logback for logging

## Directory Structure

```plaintext
src
├── main
│   ├── java
│   │   └── com.sam.customerbatchprocessor
│   │       ├── config/
│   │       ├── entity/
│   │       ├── exception/
│   │       ├── listener/
│   │       ├── repository/
│   │       └── service/
│   └── resources
│       └── customers.csv

```
## Sample customers.csv

```bash
id,name,email,status
101,John Doe,john@example.com,ACTIVE
102,Jane Doe,,INACTIVE
103,Bob Smith,bob@example.com,ACTIVE
```

## How It Works

**Reader**: Reads and maps CSV rows into `Customer` objects.

**Processor**: Validates customer data (e.g., non-empty email); throws `ValidationException` if invalid.

**Writer**: Persists valid `Customer` records in batch.

**Skip Listener**:
- Logs skipped records with reason.
- Persists failed entries into `failed_records` table.

**Job Execution Listener**:
- Exports failed entries to `failed-records.csv` if job status is `FAILED`.

---
## 🔄 Running the Job
  This batch job does not start automatically.
  Instead, trigger it manually by calling the following API endpoint:
```bash
GET http://localhost:8080/api/customerBatchProcess/start
```

## Customization

- Modify `customers.csv` to test different scenarios.
- Adjust `skipLimit` in the config to allow more or fewer skips.
- Update validation logic inside `CustomItemProcessor`.

## Example Log Output

```plaintext
Writing 2 customer records to DB
Writing: Customer(id=101, name=John Doe, ...)
Skipping item in process: Customer(id=102, name=Jane Doe, email=, ...)
Saved failed record for item: Customer(id=102, ...)
After - Job status: COMPLETED
