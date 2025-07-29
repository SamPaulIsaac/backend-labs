package com.sam.relationshiphub.manytomany.service;

import com.sam.relationshiphub.manytomany.dto.request.CourseRequestDto;
import com.sam.relationshiphub.manytomany.dto.response.CourseResponseDto;

public interface CourseService {
    CourseResponseDto createCourse(CourseRequestDto courseDto);
    CourseResponseDto getCourseById(Long id);
}
