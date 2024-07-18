package com.chamika.books_project.auth;

import jakarta.validation.constraints.NotNull;

public record AccountActivateReqBody(
        @NotNull(message = "Token is required")
        String token
) {
}
