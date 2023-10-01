package com.rogerio.serviceplatform.exception.error;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String exception) {
        super(exception);
    }
}
