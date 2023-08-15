package com.library_management.api.exception;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

public class RestClientExceptionHandler extends DefaultResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        HttpStatus status = (HttpStatus) response.getStatusCode();
        String urlString = url.toString();
        if (url.toString().length() > 200){
            urlString = url.getPath();
        }
        switch (status){
            case BAD_REQUEST: throw new BadRequestException("Bad request from "+ urlString);
            case INTERNAL_SERVER_ERROR: throw new InternalServerException("Internal Server Error "+ urlString);
            case NOT_FOUND: throw new ResourceNotFound("Unable to find the resource "+ urlString);
            case UNAUTHORIZED: throw new NotAuthorizedException("Not authorised to access "+ urlString);
            case FORBIDDEN: throw new AccessDeniedException("User does not have access "+ urlString);
            default: throw new RuntimeException("Response status: "+ status+", response"+response.getBody());
        }

    }
}
