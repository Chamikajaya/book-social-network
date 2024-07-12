package com.chamika.books_project.book;

import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public Book toBook(BookSaveRequestBody bookSaveRequest) {
        return Book.builder()
                .id(bookSaveRequest.id())
                .title(bookSaveRequest.title())
                .authorName(bookSaveRequest.authorName())
                .isbn(bookSaveRequest.isbn())
                .synopsis(bookSaveRequest.synopsis())
                .isShareable(bookSaveRequest.isShareable())
                .isArchived(false)
                .build();
    }

}
