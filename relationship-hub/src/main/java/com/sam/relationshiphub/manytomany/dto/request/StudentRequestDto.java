package com.sam.relationshiphub.manytomany.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentRequestDto {
    private String name;
    private String email;
}
