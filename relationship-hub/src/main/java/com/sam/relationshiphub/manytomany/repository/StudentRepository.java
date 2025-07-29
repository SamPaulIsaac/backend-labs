package com.sam.relationshiphub.manytomany.repository;

import com.sam.relationshiphub.manytomany.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
