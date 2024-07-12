package com.chamika.books_project.book;

import com.chamika.books_project.exceptions.ResourceNotFoundException;
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
public class BookService {

    // TODO: check whether code redundancy can be minimized regarding pagination

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    public void addBook(BookSaveRequestBody bookSaveRequest, Authentication auth) {

        User user = (User) auth.getPrincipal();
        Book book = bookMapper.toBook(bookSaveRequest);  // map the request to a book entity
        book.setOwner(user);

        bookRepository.save(book);
    }

    public BookResponseBody getBookById(Integer id) {

        Book book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book with id " + id + " not found.")
        );

        return bookMapper.toBookResponseBody(book);
    }


    public PageResponse<BookResponseBody> getAllBooksPaginated(Integer page, Integer size, Authentication auth) {


        User user = (User) auth.getPrincipal();

        // creating the pageable object
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdDateTime").descending()
        );

        Page<Book> bookPage = bookRepository.findAllDisplayableBooks(pageable, user.getId());

        List<BookResponseBody> bookResponse = bookPage.stream()
                .map(bookMapper::toBookResponseBody)
                .toList();

        return new PageResponse<>(
                bookResponse,
                bookPage.getNumber(),  // current page number
                bookPage.getSize(),  // size of the page
                bookPage.getTotalElements(),
                bookPage.getTotalPages(),
                bookPage.isFirst(),  // whether the current page is the first page
                bookPage.isLast()
        );

    }

    public PageResponse<BookResponseBody> getBooksByOwner(Integer page, Integer size, Authentication auth) {

        User user = (User) auth.getPrincipal();

        // creating the pageable object
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdDateTime").descending()
        );

        Page<Book> bookPageOfUser = bookRepository.findAllBooksByOwner(pageable, user.getId());

        List<BookResponseBody> bookResponse = bookPageOfUser.stream()
                .map(bookMapper::toBookResponseBody)
                .toList();

        return new PageResponse<>(
                bookResponse,
                bookPageOfUser.getNumber(),  // current page number
                bookPageOfUser.getSize(),  // size of the page
                bookPageOfUser.getTotalElements(),
                bookPageOfUser.getTotalPages(),
                bookPageOfUser.isFirst(),  // whether the current page is the first page
                bookPageOfUser.isLast()
        );




    }
}
