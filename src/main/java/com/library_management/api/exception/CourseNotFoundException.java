package com.library_management.api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseNotFoundException extends RuntimeException {

    private String errorCode;

    public CourseNotFoundException(final String message) {
        super(message);
    }

    public CourseNotFoundException(String errorCode, final String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
