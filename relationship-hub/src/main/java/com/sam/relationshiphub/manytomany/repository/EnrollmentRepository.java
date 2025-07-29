package com.sam.relationshiphub.manytomany.repository;

import com.sam.relationshiphub.manytomany.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
