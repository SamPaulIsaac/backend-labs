package com.sam.relationshiphub.manytomany.service.impl;

import com.sam.relationshiphub.manytomany.dto.request.EnrollmentRequestDto;
import com.sam.relationshiphub.manytomany.dto.response.EnrollmentResponseDto;
import com.sam.relationshiphub.manytomany.entity.Course;
import com.sam.relationshiphub.manytomany.entity.Enrollment;
import com.sam.relationshiphub.manytomany.entity.Student;
import com.sam.relationshiphub.manytomany.repository.CourseRepository;
import com.sam.relationshiphub.manytomany.repository.EnrollmentRepository;
import com.sam.relationshiphub.manytomany.repository.StudentRepository;
import com.sam.relationshiphub.manytomany.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Override
    public EnrollmentResponseDto enrollStudentToCourse(EnrollmentRequestDto requestDto) {
        log.debug("Enrolling student ID {} to course ID {}", requestDto.getStudentId(), requestDto.getCourseId());

        Student student = studentRepository.findById(requestDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + requestDto.getStudentId()));

        Course course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + requestDto.getCourseId()));

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .grade(requestDto.getGrade())
                .build();

        Enrollment saved = enrollmentRepository.save(enrollment);
        log.info("Enrollment successful with ID: {}", saved.getId());

        return EnrollmentResponseDto.builder()
                .id(saved.getId())
                .studentId(student.getId())
                .courseId(course.getId())
                .build();
    }
}
