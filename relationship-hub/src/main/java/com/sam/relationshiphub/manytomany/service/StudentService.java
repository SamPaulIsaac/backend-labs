package com.sam.relationshiphub.manytomany.service;

import com.sam.relationshiphub.manytomany.dto.request.StudentRequestDto;
import com.sam.relationshiphub.manytomany.dto.response.StudentResponseDto;

public interface StudentService {
    StudentResponseDto createStudent(StudentRequestDto studentDto);
    StudentResponseDto getStudentById(Long id);
}
