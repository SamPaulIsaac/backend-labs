package com.sam.basicAsync.repository;

import com.sam.basicAsync.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

