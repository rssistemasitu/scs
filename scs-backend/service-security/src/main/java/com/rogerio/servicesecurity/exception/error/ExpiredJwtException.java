package com.rogerio.servicesecurity.exception.error;

public class ExpiredJwtException extends RuntimeException{
    public ExpiredJwtException(String exception) {
        super(exception);
    }
}
