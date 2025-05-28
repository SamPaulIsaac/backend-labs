package com.sam.userService.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestDTO {
    private String name;
    private String password;
}
