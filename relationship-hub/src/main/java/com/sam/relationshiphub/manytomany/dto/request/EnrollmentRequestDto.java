package com.sam.relationshiphub.manytomany.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrollmentRequestDto {
    private Long studentId;
    private Long courseId;
    private String grade;
}
