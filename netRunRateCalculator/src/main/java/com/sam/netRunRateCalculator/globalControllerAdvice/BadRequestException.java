package com.sam.netRunRateCalculator.globalControllerAdvice;


public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
