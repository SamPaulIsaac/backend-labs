package com.sam.relationshiphub.manytomany.repository;

import com.sam.relationshiphub.manytomany.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
