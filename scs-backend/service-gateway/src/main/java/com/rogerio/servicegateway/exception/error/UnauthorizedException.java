package com.rogerio.servicegateway.exception.error;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String exception, Integer statusCode) {
        super(exception);
    }
}
