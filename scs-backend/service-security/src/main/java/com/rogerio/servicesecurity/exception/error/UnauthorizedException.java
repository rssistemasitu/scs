package com.rogerio.servicesecurity.exception.error;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String exception) {
        super(exception);
    }
}
