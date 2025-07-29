package com.sam.relationshiphub.onetoone.dto.request;

import lombok.Data;

@Data
public class EmployeeRequestDto {
    private String name;
    private String department;
    private String email;
    private EmployeeDetailRequestDto employeeDetail;
}
