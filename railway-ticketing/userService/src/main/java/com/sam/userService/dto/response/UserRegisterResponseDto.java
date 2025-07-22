package com.sam.userService.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterResponseDto {
    private Long userId;
    private String message;
    private boolean verificationSent;
}
