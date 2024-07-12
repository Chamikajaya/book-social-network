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

    public BookResponseBody toBookResponseBody(Book book) {
        return new BookResponseBody(
                book.getId(),
                book.getTitle(),
                book.getAuthorName(),
                book.getIsbn(),
                book.getSynopsis(),
                book.getIsShareable(),
                book.getIsArchived(),
                book.getOwner().getFullName(),
// TODO: Implement cover image
//                book.getCoverImage(),
                book.getAverageRating()
        );
    }
}
