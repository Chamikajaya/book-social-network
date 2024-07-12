package com.chamika.books_project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class IllegalOperationPerformException extends RuntimeException {
    public IllegalOperationPerformException(String message) {
        super(message);
    }
}
