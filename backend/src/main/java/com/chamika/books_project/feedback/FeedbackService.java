package com.chamika.books_project.feedback;


import com.chamika.books_project.book.Book;
import com.chamika.books_project.book.BookRepository;
import com.chamika.books_project.exceptions.IllegalOperationPerformException;
import com.chamika.books_project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;


    public void createFeedback(FeedbackRequestBody feedbackRequestBody, Authentication auth) {

        // check whether the book exists
        Book book = bookRepository.findById(feedbackRequestBody.bookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id " + feedbackRequestBody.bookId()));

        // check whether the user is the owner of the book
        User user = (User) auth.getPrincipal();
        if (!book.getOwner().getId().equals(user.getId())) {
            throw new IllegalOperationPerformException("You can not give a feedback to your own book. ");
        }

        // check whether the book is currently archived or not shareable
        if (!book.getIsShareable() || book.getIsArchived()) {
            throw new IllegalOperationPerformException("Unable to give a feedback since the book is currently archived or set as not shareable by the book owner.");
        }

        // create the Feedback and save it to db
        Feedback feedback = feedbackMapper.toFeedback(feedbackRequestBody);
        feedbackRepository.save(feedback);


    }
}
