package com.sam.relationshiphub.manytomany.service.impl;

import com.sam.relationshiphub.manytomany.dto.request.StudentRequestDto;
import com.sam.relationshiphub.manytomany.dto.response.StudentResponseDto;
import com.sam.relationshiphub.manytomany.entity.Student;
import com.sam.relationshiphub.manytomany.repository.StudentRepository;
import com.sam.relationshiphub.manytomany.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentResponseDto createStudent(StudentRequestDto requestDto) {
        log.debug("Persisting new student: {}", requestDto);
        Student student = Student.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .build();

        Student saved = studentRepository.save(student);
        log.info("Student saved successfully with ID: {}", saved.getId());

        return StudentResponseDto.builder()
                .id(saved.getId())
                .name(saved.getName())
                .email(saved.getEmail())
                .build();
    }

    @Override
    public StudentResponseDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));

        log.info("Found student: {}", student);

        return StudentResponseDto.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .build();
    }
}
