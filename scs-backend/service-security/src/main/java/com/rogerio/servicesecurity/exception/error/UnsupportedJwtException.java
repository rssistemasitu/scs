package com.rogerio.servicesecurity.exception.error;

public class UnsupportedJwtException extends RuntimeException{
    public UnsupportedJwtException(String exception) {
        super(exception);
    }
}
