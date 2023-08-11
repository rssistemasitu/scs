package com.rogerio.servicemain.exception.error;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String exception) {
        super(exception);
    }
}
