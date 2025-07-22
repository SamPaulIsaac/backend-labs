package com.sam.emailService.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto {


    private Long studentId;

    private String email;

    private String subject;

    private String body;
}
