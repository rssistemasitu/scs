package com.rogerio.servicesecurity.exception;

import com.rogerio.servicesecurity.exception.error.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.IllegalArgumentException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {
    LocalDateTime date = LocalDateTime.now();

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.NOT_FOUND.value(), this.date, ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        String msgError = errors.get(0).trim().substring(errors.get(0).trim().lastIndexOf(":")+2);
        String msgErrorResumed = ex.getLocalizedMessage()
                .trim()
                .substring(ex.getLocalizedMessage().lastIndexOf("Field error in object"));
        String details = msgErrorResumed.substring(0, msgErrorResumed.indexOf(";"));


        ErrorResponse apiError = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(msgError)
                .details(details)
                .timestamp(this.date)
                .build();
        return handleExceptionInternal(
                ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ErrorResponse> handlerUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), this.date, ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public final ResponseEntity<ErrorResponse> handlerForbiddenException(ForbiddenException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(FORBIDDEN.value(), this.date, ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, FORBIDDEN);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<ErrorResponse> handlerConflictException(ConflictException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.CONFLICT.value(), this.date, ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handlerUserNameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.NOT_FOUND.value(), this.date, ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SignatureException.class)
    public final ResponseEntity<ErrorResponse> handlerTokenException(SignatureException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(INTERNAL_SERVER_ERROR.value(), this.date, ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public final ResponseEntity<ErrorResponse> handlerMalformedJwtException(MalformedJwtException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(INTERNAL_SERVER_ERROR.value(), this.date, ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public final ResponseEntity<ErrorResponse> handlerExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(INTERNAL_SERVER_ERROR.value(), this.date, ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public final ResponseEntity<ErrorResponse> handlerUnsupportedJwtException(UnsupportedJwtException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(INTERNAL_SERVER_ERROR.value(), this.date, ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ErrorResponse> handlerIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(INTERNAL_SERVER_ERROR.value(), this.date, ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ErrorResponse> handlerBadRequestException(BadRequestException ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(BAD_REQUEST.value(), this.date, ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, BAD_REQUEST);
    }

}
