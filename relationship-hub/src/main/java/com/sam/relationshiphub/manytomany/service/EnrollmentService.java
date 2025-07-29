package com.sam.relationshiphub.manytomany.service;

import com.sam.relationshiphub.manytomany.dto.request.EnrollmentRequestDto;
import com.sam.relationshiphub.manytomany.dto.response.EnrollmentResponseDto;

public interface EnrollmentService {
    EnrollmentResponseDto enrollStudentToCourse(EnrollmentRequestDto dto);
}
