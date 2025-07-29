package com.sam.relationshiphub.manytomany.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentResponseDto {
    private Long id;
    private String name;
    private String email;
}
