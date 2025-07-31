package com.sam.secureotpdelivery.controller;

import com.sam.secureotpdelivery.common.response.ApiResponseBuilder;
import com.sam.secureotpdelivery.dto.SmsDeliveryStatusRequest;
import com.sam.secureotpdelivery.service.SecureOtpDeliveryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.sam.secureotpdelivery.common.util.SecureOtpDeliveryUtils.maskMobile;
import static com.sam.secureotpdelivery.common.util.SecureOtpDeliveryUtils.maskOtp;

@RestController
@RequestMapping("/api/otp")
@AllArgsConstructor
@Slf4j
@Validated
public class SecureOtpDeliveryController {

    private final SecureOtpDeliveryService secureOtpDeliveryService;

    @PostMapping("/send")
    public ResponseEntity<?> sendOtp(
            @RequestParam
            @NotBlank(message = "Mobile number must not be blank")
            @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number format")
            String mobileNumber) {

        log.info("OTP send requested for mobile: {}", maskMobile(mobileNumber));
        secureOtpDeliveryService.sendOtp(mobileNumber);
        return ApiResponseBuilder.success("OTP sent successfully");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(
            @RequestParam
            @NotBlank(message = "Mobile number must not be blank")
            @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number format")
            String mobileNumber,

            @RequestParam
            @NotBlank(message = "OTP must not be blank")
            @Pattern(regexp = "^\\d{4,6}$", message = "OTP must be 4 to 6 digits")
            String otp) {

        log.info("OTP verify requested for mobile: {}, OTP: {}", maskMobile(mobileNumber), maskOtp(otp));
        secureOtpDeliveryService.verifyOtp(mobileNumber, otp);
        return ApiResponseBuilder.success("OTP verification completed successfully");
    }

    @PostMapping("/sms/dlr")
    public ResponseEntity<?> handleDeliveryStatus(@RequestBody @Valid SmsDeliveryStatusRequest request) {
        log.info("Received SMS DLR: phone={}, status={}", maskMobile(request.getPhone()), request.getStatus());
        secureOtpDeliveryService.handleDeliveryStatus(request);
        return ApiResponseBuilder.success("Delivery report updated.");
    }

    @GetMapping("/metrics")
    public ResponseEntity<?> getMetrics() {
        log.info("Fetching OTP delivery metrics.");
        Map<String, Long> metrics = secureOtpDeliveryService.getMetrics();
        return ApiResponseBuilder.success(metrics);
    }
}
