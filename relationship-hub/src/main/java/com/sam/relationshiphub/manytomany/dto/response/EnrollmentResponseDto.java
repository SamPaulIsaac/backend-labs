package com.sam.relationshiphub.manytomany.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrollmentResponseDto {
    private Long id;
    private Long studentId;
    private Long courseId;
    private String grade;
}
