package com.chamika.books_project.auth;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record RegisterRequestBody(

        @NotBlank(message = "Please provide your first name.")
        String firstName,

        @NotBlank(message = "Please provide your last name.")
        String lastName,

        @Email(message = "Please enter a valid email address.")
        @NotBlank(message = "Email is required.")
        String email,

        @NotBlank(message = "Password is required.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "Password must be at least 8 characters long and contain a digit, lowercase letter, uppercase letter, special character and no spaces.")
        String password
) {
}
