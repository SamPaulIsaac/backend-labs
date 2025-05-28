CREATE TABLE IF NOT EXISTS verification_token(
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  token VARCHAR(50) NOT NULL,
  user_id BIGINT NOT NULL,
  expiry_date DATETIME NOT NULL,
  used boolean NOT NULL,
   CONSTRAINT fk_verification_token_user FOREIGN KEY (user_id) REFERENCES users(id)
);
