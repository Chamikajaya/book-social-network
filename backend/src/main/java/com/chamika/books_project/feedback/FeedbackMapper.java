package com.chamika.books_project.feedback;

import com.chamika.books_project.book.Book;
import org.springframework.stereotype.Service;

@Service
public class FeedbackMapper {
    public Feedback toFeedback(FeedbackRequestBody feedbackRequestBody) {
        return Feedback
                .builder()
                .rating(feedbackRequestBody.rating())
                .comment(feedbackRequestBody.comment())
                .book(Book.builder().id(feedbackRequestBody.bookId()).build())
                .build();


    }
}
