package com.library_management.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private int status;

    private String code;

    private String message;

    @JsonIgnore
    private Exception exception;

    public ErrorResponse(int status, Exception e) {
        this.status = status;
        this.exception = e;
        this.message = exception.getMessage();

    }

    public ErrorResponse(int status, String code, Exception exception) {
        this(status,exception);
        this.code = code;
    }


    public ErrorResponse(int status, String code, String message, Exception exception) {
        this(status,code,exception);
        this.message = message;
    }

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
