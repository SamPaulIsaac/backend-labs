package com.sam.secureotpdelivery.common.util;

public class SecureOtpDeliveryUtils {

    public static String maskMobile(String mobile) {
        if (mobile == null || mobile.length() < 10) return "**********";
        return mobile.replaceAll("(\\d{2})\\d{6}(\\d{2})", "$1******$2");
    }

    public static String maskOtp(String otp) {
        if (otp == null || otp.isEmpty()) return "****";
        return "*".repeat(otp.length());
    }
}
