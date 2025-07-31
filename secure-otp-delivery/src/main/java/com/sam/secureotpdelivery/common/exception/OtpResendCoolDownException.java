package com.sam.secureotpdelivery.common.exception;

public class OtpResendCoolDownException extends RuntimeException {
    public OtpResendCoolDownException(String message) {
        super(message);
    }
}
