package com.job.exceptions;

public class DuplicateFoundException extends Exception{

    public DuplicateFoundException() {
    }

    public DuplicateFoundException(String message) {
        super(message);
    }

    public DuplicateFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
