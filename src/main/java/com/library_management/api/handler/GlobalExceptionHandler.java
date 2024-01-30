package com.library_management.api.handler;

import com.library_management.api.exception.CustomDatabaseException;
import com.library_management.api.exception.ErrorResponse;
import com.library_management.api.exception.NoInputValueException;
import com.library_management.api.exception.NotAuthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.*;

@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(CustomDatabaseException.class)
    @ResponseBody
    public ErrorResponse handleCustomDatabaseException(CustomDatabaseException ex) {
        return new ErrorResponse(BAD_REQUEST.value(), ex);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ErrorResponse illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        return new ErrorResponse(BAD_REQUEST.value(), ex);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(NoInputValueException.class)
    @ResponseBody
    public ErrorResponse handleNoInputValueException(NoInputValueException ex) {
        return new ErrorResponse(BAD_REQUEST.value(), ex);
    }


    @ResponseStatus(METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ErrorResponse(METHOD_NOT_ALLOWED.value(), String.valueOf(METHOD_NOT_ALLOWED));
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseBody
    public ErrorResponse NotAuthorizedExceptionHandler(NotAuthorizedException notAuthorizedException) {
        return new ErrorResponse(UNAUTHORIZED.value(), notAuthorizedException);
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ErrorResponse AccessDeniedExceptionHandler(AccessDeniedException accessDeniedException) {
        return new ErrorResponse(FORBIDDEN.value(), accessDeniedException);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse UncaughtExceptionHandler(Exception ex) {
        return new ErrorResponse(INTERNAL_SERVER_ERROR.value(), ex);
    }
}