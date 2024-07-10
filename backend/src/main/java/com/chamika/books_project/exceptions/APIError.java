package com.chamika.books_project.exceptions;

import java.time.LocalDateTime;

public record APIError(
        String path,
        String message,
        int statusCode,
        LocalDateTime timestamp
) {
}
