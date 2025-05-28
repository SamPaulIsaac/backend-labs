package com.sam.userService.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterRequestDto {

    private String name;
    private String email;
    private String password;
    private Long contactNumber;
}
