package com.chamika.books_project.book;

import jakarta.validation.constraints.NotBlank;

public record BookSaveRequestBody(

        Integer id,  // not required for saving, but required for updating

        @NotBlank(message = "Please provide book title.")
        String title,

        @NotBlank(message = "Please provide author name.")
        String authorName,

        @NotBlank(message = "Please provide ISBN.")
        String isbn,

        @NotBlank(message = "Please provide synopsis.")
        String synopsis,

        Boolean isShareable


        ) {
}
