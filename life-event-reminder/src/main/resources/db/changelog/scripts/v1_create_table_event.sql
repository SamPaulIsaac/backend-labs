-- ChangeSet 001 - Author: sampaul
CREATE TABLE IF NOT EXISTS events (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    event_date DATE,
    recipients TEXT,
    channel VARCHAR(50),
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    created_at DATETIME,
    updated_at DATETIME,
    CHECK (channel IN ('EMAIL', 'WHATSAPP', 'BOTH'))
);
