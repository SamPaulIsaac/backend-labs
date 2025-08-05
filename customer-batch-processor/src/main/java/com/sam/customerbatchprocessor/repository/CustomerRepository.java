package com.sam.customerbatchprocessor.repository;

import com.sam.customerbatchprocessor.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}

