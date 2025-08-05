CREATE TABLE failed_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_data JSON NOT NULL,
    error_message VARCHAR(1000) NOT NULL,
    job_name VARCHAR(255) NOT NULL,
    job_execution_id BIGINT NOT NULL,
    file_reference VARCHAR(255) NOT NULL,
    failed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);