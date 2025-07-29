package com.sam.relationshiphub.onetoone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetailResponseDto {
    private Long id;
    private String address;
    private String dob;
    private String aadharNumber;
}
