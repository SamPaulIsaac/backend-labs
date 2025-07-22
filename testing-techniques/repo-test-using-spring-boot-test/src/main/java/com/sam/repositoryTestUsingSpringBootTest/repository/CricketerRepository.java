package com.sam.repositoryTestUsingSpringBootTest.repository;

import com.sam.repositoryTestUsingSpringBootTest.entity.Cricketer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CricketerRepository extends JpaRepository<Cricketer, Long> {
}

