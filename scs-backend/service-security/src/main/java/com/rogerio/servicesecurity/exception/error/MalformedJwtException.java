package com.rogerio.servicesecurity.exception.error;

public class MalformedJwtException extends RuntimeException{
    public MalformedJwtException(String exception) {
        super(exception);
    }
}
