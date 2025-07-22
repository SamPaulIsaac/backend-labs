CREATE TABLE IF NOT EXISTS net_run_rate (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nrr DOUBLE NOT NULL,
    team_id BIGINT NOT NULL,
     CONSTRAINT fk_net_run_rate_team FOREIGN KEY (team_id) REFERENCES team(id)
);