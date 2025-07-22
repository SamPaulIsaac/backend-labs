package com.sam.alarmApplication.globalResponseHandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiSuccessDto<T> {
    private int status;
    private T data;
}
