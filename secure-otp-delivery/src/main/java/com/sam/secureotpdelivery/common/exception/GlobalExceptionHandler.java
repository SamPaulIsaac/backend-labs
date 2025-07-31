package com.sam.secureotpdelivery.common.exception;

import com.sam.secureotpdelivery.common.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = IncorrectMobileNumberException.class)
    public ResponseEntity<ApiErrorResponse> handleIncorrectMobileNumberException(IncorrectMobileNumberException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(exception = InvalidOtpException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidOtpException(InvalidOtpException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(exception = IncorrectOtpException.class)
    public ResponseEntity<ApiErrorResponse> handleIncorrectOtpException(IncorrectOtpException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(exception = OtpResendCoolDownException.class)
    public ResponseEntity<ApiErrorResponse> handleOtpResendCoolDownException(OtpResendCoolDownException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGlobalException(Exception exception) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        ApiErrorResponse response = ApiErrorResponse
                .builder()
                .status(status.value())
                .error(message)
                .build();
        return new ResponseEntity<>(response, status);
    }
}
