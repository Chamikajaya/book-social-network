package com.chamika.books_project.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIError> handleException(
            BadRequestException e,
            HttpServletRequest request
    ) {

        log.error("Bad request exception occurred", e);
        APIError apiError = new APIError(
                request.getRequestURL().toString(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<APIError> handleException(
            EmailAlreadyTakenException e,
            HttpServletRequest request
    ) {

        log.error("Bad request exception occurred", e);
        APIError apiError = new APIError(
                request.getRequestURL().toString(),
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> handleException(
            ResourceNotFoundException e,
            HttpServletRequest request
    ) {

        log.error("Bad request exception occurred", e);
        APIError apiError = new APIError(
                request.getRequestURL().toString(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);

    }

}
