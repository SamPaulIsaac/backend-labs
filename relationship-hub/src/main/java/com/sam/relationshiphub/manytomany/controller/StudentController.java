package com.sam.relationshiphub.manytomany.controller;

import com.sam.relationshiphub.manytomany.dto.request.StudentRequestDto;
import com.sam.relationshiphub.manytomany.dto.response.StudentResponseDto;
import com.sam.relationshiphub.manytomany.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public StudentResponseDto createStudent(@RequestBody StudentRequestDto requestDto) {
        log.info("Creating student with data: {}", requestDto);
        StudentResponseDto response = studentService.createStudent(requestDto);
        log.info("Student created with ID: {}", response.getId());
        return response;
    }

    @GetMapping("/{id}")
    public StudentResponseDto getStudentById(@PathVariable Long id) {
        log.info("Fetching student with ID: {}", id);
        StudentResponseDto response = studentService.getStudentById(id);
        log.info("Fetched student: {}", response);
        return response;
    }
}
