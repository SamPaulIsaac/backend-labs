CREATE TABLE student (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL
);


CREATE TABLE course (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    credits VARCHAR(50) NOT NULL
);


CREATE TABLE enrollment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    grade VARCHAR(50) NOT NULL,
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES student(id),
    CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES course(id),
    CONSTRAINT uq_student_course UNIQUE (student_id, course_id)
);
