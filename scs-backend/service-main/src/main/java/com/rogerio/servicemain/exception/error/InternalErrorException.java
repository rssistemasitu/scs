package com.rogerio.servicemain.exception.error;

public class InternalErrorException extends RuntimeException{
    public InternalErrorException(String exception) {
        super(exception);
    }
}
