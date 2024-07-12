package com.chamika.books_project.book;

import com.chamika.books_project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    public void addBook(BookSaveRequestBody bookSaveRequest, Authentication currentUser) {

        User user = (User) currentUser.getPrincipal();
        Book book = bookMapper.toBook(bookSaveRequest);  // map the request to a book entity
        book.setOwner(user);

        bookRepository.save(book);

    }
}
