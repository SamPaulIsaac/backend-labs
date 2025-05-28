package com.sam.userService.exceptions;


public class TokenUsedAlreadyException extends RuntimeException {
    public TokenUsedAlreadyException(String message) {
        super(message);
    }
}
