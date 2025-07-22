CREATE TABLE url_shortener (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  -- id will be manually set during sync
    original_url VARCHAR(2048) NOT NULL,
    -- shortened_url VARCHAR(100) NOT NULL UNIQUE,
    shortened_url VARCHAR(100) NULL,
    expiration TIMESTAMP NOT NULL,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL
);
