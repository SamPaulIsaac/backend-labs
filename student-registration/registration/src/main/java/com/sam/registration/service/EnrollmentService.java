package com.sam.registration.service;

import com.sam.registration.entity.Enrollment;
import com.sam.registration.repository.EnrollmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public Enrollment createEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }
}
