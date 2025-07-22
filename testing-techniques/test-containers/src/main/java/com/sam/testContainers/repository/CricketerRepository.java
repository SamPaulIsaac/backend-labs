package com.sam.testContainers.repository;

import com.sam.testContainers.entity.Cricketer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CricketerRepository extends JpaRepository<Cricketer, Long> {
}

