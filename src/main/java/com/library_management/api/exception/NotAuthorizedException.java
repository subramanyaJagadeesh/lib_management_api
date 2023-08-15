package com.library_management.api.exception;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException(final String message) {
        super(message);
    }
}
