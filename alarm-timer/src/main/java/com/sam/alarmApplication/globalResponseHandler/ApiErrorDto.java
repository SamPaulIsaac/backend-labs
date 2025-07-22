package com.sam.alarmApplication.globalResponseHandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiErrorDto {
    private int status;
    private String message;
}
