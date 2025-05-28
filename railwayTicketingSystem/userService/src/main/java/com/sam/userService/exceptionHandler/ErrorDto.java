package com.sam.userService.exceptionHandler;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    private int status;
    private String message;
}
