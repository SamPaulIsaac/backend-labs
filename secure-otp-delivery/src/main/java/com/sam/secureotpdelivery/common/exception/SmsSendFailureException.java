package com.sam.secureotpdelivery.common.exception;

public class SmsSendFailureException extends RuntimeException {
    public SmsSendFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
