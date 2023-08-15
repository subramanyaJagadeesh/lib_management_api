package com.library_management.api.exception;

public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(final String message) {
        super(message);
    }
}
