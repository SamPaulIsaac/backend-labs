package com.sam.netRunRateCalculator.globalControllerAdvice;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorDto {
    @NotNull(message = "Status must not be blank")
    private int status;
    @NotBlank(message = "Message must not be blank")
    private String message;
}
