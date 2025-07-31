package com.sam.secureotpdelivery.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiErrorResponse {
    private int status;
    private String error;
}
