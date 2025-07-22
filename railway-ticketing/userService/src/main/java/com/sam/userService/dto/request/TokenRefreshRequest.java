package com.sam.userService.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRefreshRequest {

    private String refreshToken;

}
