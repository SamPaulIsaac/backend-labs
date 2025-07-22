package com.sam.taskManagement.exception;

public class InvalidTaskStatusRequestException extends RuntimeException{

    public InvalidTaskStatusRequestException(String message){
        super(message);
    }
}
