package com.chamika.books_project.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIError> handleException(BadRequestException e, HttpServletRequest request) {

        log.error("Bad request exception occurred", e);
        APIError apiError = new APIError(request.getRequestURL().toString(), e.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<APIError> handleException(EmailAlreadyTakenException e, HttpServletRequest request) {

        log.error("Bad request exception occurred", e);
        APIError apiError = new APIError(request.getRequestURL().toString(), e.getMessage(), HttpStatus.CONFLICT.value(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> handleException(ResourceNotFoundException e, HttpServletRequest request) {

        log.error("Bad request exception occurred", e);
        APIError apiError = new APIError(request.getRequestURL().toString(), e.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIError> handleException(BadCredentialsException e, HttpServletRequest request) {
        log.error("Bad Credentials provided ", e);

        APIError apiError = new APIError(request.getRequestURI(), e.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }



    // * Catch all exceptions handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleException(Exception e, HttpServletRequest request) {
        log.error("Unhandled Exception Occurred ", e);

        APIError apiError = new APIError(request.getRequestURI(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
