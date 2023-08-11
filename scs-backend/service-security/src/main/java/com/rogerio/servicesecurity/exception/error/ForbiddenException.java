package com.rogerio.servicesecurity.exception.error;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String exception) {
        super(exception);
    }
}
