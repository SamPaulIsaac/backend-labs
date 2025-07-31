package com.sam.secureotpdelivery.common.constant;

import java.time.Duration;

public class SecureOtpDeliveryConstant {
    public static String MOBILE_REGEX_PATTERN = "^(?:\\+91|91|0)?[6-9]\\d{9}$";
    public static int OTP_MAX_LENGTH = 6;
    public static int OTP_VERIFY_MAX_ATTEMPTS = 3;
    public static long RESEND_COOLDOWN_SECONDS = 60;
    public static Duration OTP_TTL = Duration.ofMinutes(5);

}
