package com.sam.netRunRateCalculator.globalControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ApiErrorDto> handleNotEntityFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ApiErrorDto.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }
}
