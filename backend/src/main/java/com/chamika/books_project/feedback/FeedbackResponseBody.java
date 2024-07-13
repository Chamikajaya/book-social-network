package com.chamika.books_project.feedback;

public record FeedbackResponseBody(
        Integer rating,
        String comment,

        Boolean isFeedbackBelongsToCurrUser

) {
}
