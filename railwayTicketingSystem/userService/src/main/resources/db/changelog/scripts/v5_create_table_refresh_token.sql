CREATE TABLE refresh_tokens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(512) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL UNIQUE,
    expiry_date TIMESTAMP NOT NULL,

    CONSTRAINT fk_refresh_token_user FOREIGN KEY (user_id)
        REFERENCES users(id) ON DELETE CASCADE
);
