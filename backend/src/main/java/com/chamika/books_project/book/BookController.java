package com.chamika.books_project.book;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(
            @Valid @RequestBody BookSaveRequestBody bookSaveRequest,
            Authentication currentUser
    ) {
        bookService.addBook(bookSaveRequest, currentUser);
    }


}
