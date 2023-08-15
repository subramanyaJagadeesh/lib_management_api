package com.library_management.api.exception;

public class CustomDatabaseException extends RuntimeException{
    public CustomDatabaseException(String message) {
        super(message);
    }
}
