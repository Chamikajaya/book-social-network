package com.chamika.books_project.feedback;

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


}
