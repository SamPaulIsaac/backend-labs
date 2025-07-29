package com.sam.relationshiphub.onetoone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String department;

    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_detail_id", referencedColumnName = "id")
    private EmployeeDetail employeeDetail;


}
