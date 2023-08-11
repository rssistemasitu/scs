package com.rogerio.servicesecurity.exception.error;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String exception) {
        super(exception);
    }
}
