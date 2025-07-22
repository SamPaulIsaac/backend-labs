CREATE TABLE IF NOT EXISTS alarms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    time DATETIME NOT NULL,
    is_recurring BOOLEAN NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    recurrence_pattern VARCHAR(50) NOT NULL,
    ringtone VARCHAR(255),
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    created_at DATETIME,
    updated_at DATETIME,
    CHECK (recurrence_pattern IN ('DAILY', 'WEEKLY', 'MONTHLY'))
);
