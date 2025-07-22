package com.sam.taskManagement.exception;

public class InvalidTaskStatusTransitionException extends RuntimeException{

    public InvalidTaskStatusTransitionException(String message){
        super(message);
    }
}
