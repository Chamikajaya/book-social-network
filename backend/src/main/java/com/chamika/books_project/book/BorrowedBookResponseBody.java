package com.chamika.books_project.book;

public record BorrowedBookResponseBody(
        Integer id,
        String title,
        String authorName,
        String isbn,
        Double averageRating,
        Boolean isReturned,
        Boolean isReturnApproved


) {
}
