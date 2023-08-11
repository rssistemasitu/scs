package com.rogerio.servicegateway.exception.error;

public class InternalErrorException extends RuntimeException{
    public InternalErrorException(String exception, Integer statusCode) {
        super(exception);
    }
}
