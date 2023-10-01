package com.rogerio.serviceplatform.exception;

import com.rogerio.serviceplatform.exception.error.BadRequestException;
import com.rogerio.serviceplatform.exception.error.ErrorResponse;
import com.rogerio.serviceplatform.exception.error.InternalErrorException;
import com.rogerio.serviceplatform.exception.error.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {
    LocalDateTime date = LocalDateTime.now();

    @ExceptionHandler(InternalErrorException.class)
    public final ResponseEntity<ErrorResponse> handleInternalServerException(InternalErrorException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(INTERNAL_SERVER_ERROR.value(), this.date, ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.NOT_FOUND.value(), this.date, ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ErrorResponse> handlerBadRequestException(BadRequestException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(BAD_REQUEST.value(), this.date, ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ErrorResponse> handlerIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(INTERNAL_SERVER_ERROR.value(), this.date, ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, INTERNAL_SERVER_ERROR);
    }



}
