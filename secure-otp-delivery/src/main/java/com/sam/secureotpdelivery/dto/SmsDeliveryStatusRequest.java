package com.sam.secureotpdelivery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SmsDeliveryStatusRequest {

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number format")
    private String phone;

    @NotBlank(message = "Status must not be blank")
    private String status;
}
