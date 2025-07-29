package com.sam.relationshiphub.onetoone.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDetailRequestDto {
    private String address;

    private String dob;

    private String aadharNumber;
}
