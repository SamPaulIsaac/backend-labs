package com.sam.relationshiphub.manytomany.controller;

import com.sam.relationshiphub.manytomany.dto.request.EnrollmentRequestDto;
import com.sam.relationshiphub.manytomany.dto.response.EnrollmentResponseDto;
import com.sam.relationshiphub.manytomany.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Slf4j
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public EnrollmentResponseDto enrollStudent(@RequestBody EnrollmentRequestDto requestDto) {
        log.info("Enrolling student ID {} to course ID {}, {}", requestDto.getStudentId(), requestDto.getCourseId(), requestDto.getGrade());
        EnrollmentResponseDto response = enrollmentService.enrollStudentToCourse(requestDto);
        log.info("Enrollment successful with ID: {}", response.getId());
        return response;
    }
}
