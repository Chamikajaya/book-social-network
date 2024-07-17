package com.chamika.books_project.feedback;


import com.chamika.books_project.book.Book;
import com.chamika.books_project.book.BookRepository;
import com.chamika.books_project.exceptions.IllegalOperationPerformException;
import com.chamika.books_project.shared.PageResponse;
import com.chamika.books_project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;


    public void createFeedback(FeedbackRequestBody feedbackRequestBody, Authentication auth) {

        // check whether the book exists
        Book book = bookRepository.findById(feedbackRequestBody.bookId()).orElseThrow(() -> new IllegalArgumentException("Book not found with id " + feedbackRequestBody.bookId()));

        // check whether the user is the owner of the book
        User user = (User) auth.getPrincipal();
        if (book.getOwner().getId().equals(user.getId())) {
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


    public PageResponse<FeedbackResponseBody> getAllFeedbacksByBook(Integer bookId, int page, int size, Authentication auth) {

        // check whether the book exists
        bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found with id " + bookId));

        User user = (User) auth.getPrincipal();

        // creating the pageable object
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDateTime").descending());

        Page<Feedback> feedbacksForThisBook = feedbackRepository.findAllFeedbacksRelatedToABook(bookId, pageable);

        List<FeedbackResponseBody> feedbackResponseBodies = feedbacksForThisBook.stream().map(
                (Feedback feedback) -> feedbackMapper.toFeedbackResponseBody(feedback, user.getId())
        ).toList();

        return new PageResponse<>(
                feedbackResponseBodies,
                feedbacksForThisBook.getNumber(),
                feedbacksForThisBook.getSize(),
                feedbacksForThisBook.getTotalElements(),
                feedbacksForThisBook.getTotalPages(),
                feedbacksForThisBook.isFirst(),
                feedbacksForThisBook.isLast()


        );
    }
}
