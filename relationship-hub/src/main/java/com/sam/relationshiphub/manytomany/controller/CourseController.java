package com.sam.relationshiphub.manytomany.controller;

import com.sam.relationshiphub.manytomany.dto.request.CourseRequestDto;
import com.sam.relationshiphub.manytomany.dto.response.CourseResponseDto;
import com.sam.relationshiphub.manytomany.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public CourseResponseDto createCourse(@RequestBody CourseRequestDto requestDto) {
        log.info("Creating course with data: {}", requestDto);
        CourseResponseDto response = courseService.createCourse(requestDto);
        log.info("Course created with ID: {}", response.getId());
        return response;
    }

    @GetMapping("/{id}")
    public CourseResponseDto getCourseById(@PathVariable Long id) {
        log.info("Fetching course with ID: {}", id);
        CourseResponseDto response = courseService.getCourseById(id);
        log.info("Fetched course: {}", response);
        return response;
    }
}
