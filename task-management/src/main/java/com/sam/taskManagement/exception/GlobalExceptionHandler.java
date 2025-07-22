package com.sam.taskManagement.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity handleTaskNotFoundException(TaskNotFoundException taskNotFoundException) {
        return new ResponseEntity(taskNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTaskStatusTransitionException.class)
    public ResponseEntity handleInvalidTaskStatusTransitionException(InvalidTaskStatusTransitionException invalidTaskStatusTransitionException) {
        return new ResponseEntity(invalidTaskStatusTransitionException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
