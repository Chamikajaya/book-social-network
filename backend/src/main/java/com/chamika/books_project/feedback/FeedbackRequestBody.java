package com.chamika.books_project.feedback;

import jakarta.validation.constraints.*;

public record FeedbackRequestBody(

        @Min(value = 1, message = "Rating must be at least 1.")
        @Max(value = 5, message = "Rating must be at most 5.")
        Integer rating,

        @NotBlank(message = "Comment cannot be blank.")
        @NotNull(message = "Comment cannot be null.")
        String comment,

        @NotNull(message = "Book ID cannot be null.")
        Integer bookId
) {
}