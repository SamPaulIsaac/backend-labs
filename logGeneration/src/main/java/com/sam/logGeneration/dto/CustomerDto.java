package com.sam.logGeneration.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDto {


    private String name;

    private Integer age;

    private String gender;

    private String country;

    private Long mobileNo;

    private String email;

}
