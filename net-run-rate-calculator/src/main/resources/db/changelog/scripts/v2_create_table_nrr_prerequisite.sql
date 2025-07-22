CREATE TABLE IF NOT EXISTS nrr_prerequisite (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  match_number BIGINT NOT NULL,
  no_of_runs_scored BIGINT NOT NULL,
  no_of_overs_consumed DOUBLE NOT NULL,
  no_of_runs_given BIGINT NOT NULL,
  no_of_overs_bowled DOUBLE NOT NULL,
  team_id BIGINT NOT NULL,
  CONSTRAINT fk_nrr_prerequisite_team FOREIGN KEY (team_id) REFERENCES team(id)
);