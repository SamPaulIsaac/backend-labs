package com.sam.relationshiphub.manytomany.service.impl;

import com.sam.relationshiphub.manytomany.dto.request.CourseRequestDto;
import com.sam.relationshiphub.manytomany.dto.response.CourseResponseDto;
import com.sam.relationshiphub.manytomany.entity.Course;
import com.sam.relationshiphub.manytomany.repository.CourseRepository;
import com.sam.relationshiphub.manytomany.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public CourseResponseDto createCourse(CourseRequestDto requestDto) {
        log.debug("Persisting new course: {}", requestDto);

        Course course = Course.builder()
                .name(requestDto.getName())
                .credits(requestDto.getCredits())
                .build();

        Course saved = courseRepository.save(course);
        log.info("Course saved successfully with ID: {}", saved.getId());

        return CourseResponseDto.builder()
                .id(saved.getId())
                .name(saved.getName())
                .credits(saved.getCredits())
                .build();
    }

    @Override
    public CourseResponseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + id));

        log.info("Found course: {}", course);

        return CourseResponseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .credits(course.getCredits())
                .build();
    }
}
