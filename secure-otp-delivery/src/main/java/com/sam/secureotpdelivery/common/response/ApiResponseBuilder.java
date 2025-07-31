package com.sam.secureotpdelivery.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseBuilder {

    public static <T> ResponseEntity<ApiSuccessResponse<T>> success(T data) {
        return ResponseEntity.ok(ApiSuccessResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .data(data)
                .build());
    }

    public static ResponseEntity<ApiErrorResponse> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(ApiErrorResponse.builder()
                .status(status.value())
                .error(message)
                .build());
    }
}

