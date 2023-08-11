package com.rogerio.servicepayment.exception.error;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String exception) {
        super(exception);
    }
}
