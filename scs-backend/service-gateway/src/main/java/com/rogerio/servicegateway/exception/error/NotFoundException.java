package com.rogerio.servicegateway.exception.error;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String exception, Integer statusCode) {
        super(exception);
    }
}
