package com.sam.relationshiphub.onetoone.repository;

import com.sam.relationshiphub.onetoone.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long>{
}
