package com.sam.relationshiphub.onetoone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "employee_detail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String dob;

    private String aadharNumber;
}
