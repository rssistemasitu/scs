package com.rogerio.servicegateway.exception.error;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String exception, Integer statusCode) {
        super(exception);
    }
}
