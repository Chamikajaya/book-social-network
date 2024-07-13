package com.chamika.books_project.feedback;

import com.chamika.books_project.shared.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createFeedback(
            @Valid @RequestBody FeedbackRequestBody FeedbackRequestBody,
            Authentication auth
    ) {

        feedbackService.createFeedback(FeedbackRequestBody, auth);
    }

    @GetMapping("/book/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<FeedbackResponseBody> getAllFeedbacksByBook(
            @PathVariable("bookId") Integer bookId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "8", required = false) int size,
            Authentication auth
    ) {

        return feedbackService.getAllFeedbacksByBook(bookId, page, size, auth);
    }



}
