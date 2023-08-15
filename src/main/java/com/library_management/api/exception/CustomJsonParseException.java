package com.library_management.api.exception;

public class CustomJsonParseException extends RuntimeException{

    public CustomJsonParseException(final String message){
        super(message);
    }
}
