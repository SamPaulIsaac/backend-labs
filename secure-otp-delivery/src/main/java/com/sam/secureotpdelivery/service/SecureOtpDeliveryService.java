package com.sam.secureotpdelivery.service;

import com.sam.secureotpdelivery.common.exception.IncorrectMobileNumberException;
import com.sam.secureotpdelivery.common.exception.IncorrectOtpException;
import com.sam.secureotpdelivery.common.exception.InvalidOtpException;
import com.sam.secureotpdelivery.common.exception.OtpResendCoolDownException;
import com.sam.secureotpdelivery.dto.SmsDeliveryStatusRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.sam.secureotpdelivery.common.constant.SecureOtpDeliveryConstant.*;
import static com.sam.secureotpdelivery.common.util.SecureOtpDeliveryUtils.maskMobile;
import static com.sam.secureotpdelivery.common.util.SecureOtpDeliveryUtils.maskOtp;

@Service
@AllArgsConstructor
@Slf4j
public class SecureOtpDeliveryService {

    private final StringRedisTemplate redisTemplate;
    private final SmsSenderService smsSenderService;

    public String sendOtp(String mobileNumber) {
        validateMobileNumber(mobileNumber);

        long now = Instant.now().getEpochSecond();
        String otpKey = "OTP:" + mobileNumber;
        String otpRequestKey = "OTP_REQUEST:" + mobileNumber;
        String resendKey = "OTP_RESEND:" + mobileNumber;

        String lastSent = redisTemplate.opsForValue().get(resendKey);
        if (lastSent != null) {
            long lastEpoch = Long.parseLong(lastSent);
            long waitTime = RESEND_COOLDOWN_SECONDS - (now - lastEpoch);
            if (waitTime > 0) {
                throw new OtpResendCoolDownException(
                        String.format("Please wait %d seconds before requesting a new OTP.", waitTime));
            }
        }

        String generatedOtp = generateOtp(OTP_MAX_LENGTH);
        redisTemplate.opsForValue().set(otpKey, generatedOtp, OTP_TTL);
        redisTemplate.opsForValue().set(otpRequestKey, UUID.randomUUID().toString(), OTP_TTL);
        redisTemplate.opsForValue().set(resendKey, String.valueOf(now), OTP_TTL);

        smsSenderService.sendOtpSms(mobileNumber, "Your OTP is: " + generatedOtp);

        log.info("OTP generated and sent. mobile={}, otp={}", maskMobile(mobileNumber), maskOtp(generatedOtp));
        audit(mobileNumber, "OTP sent");

        redisTemplate.opsForValue().increment("METRIC:OTP_SENT");
        return generatedOtp;
    }

    public void verifyOtp(String mobileNumber, String otp) {
        String otpKey = "OTP:" + mobileNumber;
        String otpRequestKey = "OTP_REQUEST:" + mobileNumber;
        String resendKey = "OTP_RESEND:" + mobileNumber;
        String attemptKey = "OTP_ATTEMPTS:" + mobileNumber;

        String storedOtp = redisTemplate.opsForValue().get(otpKey);

        if (storedOtp == null) {
            audit(mobileNumber, "Invalid OTP attempt: " + maskOtp(otp));
            redisTemplate.opsForValue().increment("METRIC:OTP_FAILED");
            throw new InvalidOtpException("OTP expired or not requested.");
        }

        if (storedOtp.equals(otp)) {
            redisTemplate.delete(otpKey);
            redisTemplate.delete(otpRequestKey);
            redisTemplate.delete(resendKey);
            redisTemplate.delete(attemptKey);

            audit(mobileNumber, "OTP verified successfully");
            redisTemplate.opsForValue().increment("METRIC:OTP_VERIFIED");

            log.info("OTP verified. Cleaned up redis keys for mobile={}", maskMobile(mobileNumber));
        } else {
            Long attempts = redisTemplate.opsForValue().increment(attemptKey);
            if (attempts == 1) {
                redisTemplate.expire(attemptKey, OTP_TTL);
            }

            audit(mobileNumber, "Invalid OTP: " + maskOtp(otp));
            redisTemplate.opsForValue().increment("METRIC:OTP_FAILED");

            if (attempts >= OTP_VERIFY_MAX_ATTEMPTS) {
                redisTemplate.delete(otpKey);
                redisTemplate.delete(otpRequestKey);
                redisTemplate.delete(attemptKey);
                redisTemplate.delete(resendKey);

                log.warn("OTP blocked for mobile={} after {} failed attempts", maskMobile(mobileNumber), attempts);
                audit(mobileNumber, "OTP blocked due to max failures");
                throw new IncorrectOtpException(
                        String.format("OTP verification blocked for %s after %d failed attempts.",
                                maskMobile(mobileNumber), attempts));
            }

            throw new IncorrectOtpException(
                    String.format("Invalid OTP. Remaining attempts for %s: %d",
                            maskMobile(mobileNumber), (OTP_VERIFY_MAX_ATTEMPTS - attempts)));
        }
    }

    public void handleDeliveryStatus(SmsDeliveryStatusRequest request) {
        redisTemplate.opsForValue().set("DLR:" + request.getPhone(), request.getStatus());
        log.info("SMS delivery status updated. phone={}, status={}",
                maskMobile(request.getPhone()), request.getStatus());
    }

    public Map<String, Long> getMetrics() {
        return Map.of(
                "sent", safeGet("METRIC:OTP_SENT"),
                "verified", safeGet("METRIC:OTP_VERIFIED"),
                "failed", safeGet("METRIC:OTP_FAILED")
        );
    }

    private Long safeGet(String key) {
        String value = redisTemplate.opsForValue().get(key);
        return value != null ? Long.parseLong(value) : 0L;
    }

    private void validateMobileNumber(String mobileNumber) {
        boolean valid = Pattern.matches(MOBILE_REGEX_PATTERN, mobileNumber);
        if (!valid) {
            throw new IncorrectMobileNumberException("Invalid mobile number format: " + maskMobile(mobileNumber));
        }
    }

    private String generateOtp(int otpLength) {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.ints(otpLength, 0, 10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }

    private void audit(String phone, String event) {
        String logEntry = Instant.now() + " - " + event;
        redisTemplate.opsForList().leftPush("AUDIT:" + phone, logEntry);
    }
}
