package com.sam.secureotpdelivery.common.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ApiSuccessResponse<T> {
    private int status;
    private T data;
}

