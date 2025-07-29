CREATE TABLE employee_detail(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(50),
    dob VARCHAR(50),
    aadhar_number VARCHAR(20)
);

CREATE TABLE employee(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    department VARCHAR(50),
    email VARCHAR(50),
    employee_detail_id BIGINT,
    CONSTRAINT fk_employee_detail FOREIGN KEY (employee_detail_id) REFERENCES employee_detail(id)
);