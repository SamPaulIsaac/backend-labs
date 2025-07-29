CREATE TABLE project    (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50),
    dead_line DATE
    );

CREATE TABLE task(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(50),
    status VARCHAR(50),
     project_id BIGINT,
     CONSTRAINT fk_project FOREIGN KEY(project_id) REFERENCES project(id)
);