package com.chamika.books_project.book;

import com.chamika.books_project.exceptions.IllegalOperationPerformException;
import com.chamika.books_project.exceptions.ResourceNotFoundException;
import com.chamika.books_project.shared.PageResponse;
import com.chamika.books_project.transactions.BookTransaction;
import com.chamika.books_project.transactions.BookTransactionRepository;
import com.chamika.books_project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    // TODO: check whether code redundancy can be minimized regarding pagination

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookTransactionRepository bookTransactionRepository;

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

    // ***************************************************************************************************************

    public PageResponse<BorrowedBookResponseBody> getAllBorrowedBooksOfUser(Integer page, Integer size, Authentication auth) {

        User user = (User) auth.getPrincipal();

        // creating the pageable object
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdDateTime").descending()
        );

        Page<BookTransaction> transactionsPageForThisUser = bookTransactionRepository.findAllTransactionsRelatedToThisUser(pageable, user.getId());

        List<BorrowedBookResponseBody> borrowedBookResponse = transactionsPageForThisUser.stream()
                .map(bookMapper::toBorrowedBookResponseBody)
                .toList();

        return new PageResponse<>(
                borrowedBookResponse,
                transactionsPageForThisUser.getNumber(),  // current page number
                transactionsPageForThisUser.getSize(),  // size of the page
                transactionsPageForThisUser.getTotalElements(),
                transactionsPageForThisUser.getTotalPages(),
                transactionsPageForThisUser.isFirst(),  // whether the current page is the first page
                transactionsPageForThisUser.isLast()
        );


    }

    // TODO: check this method in controller & TransactionHistoryRepo ==>

    public PageResponse<BorrowedBookResponseBody> getAllReturnedBooksOfUser(Integer page, Integer size, Authentication auth) {

        User user = (User) auth.getPrincipal();

        // creating the pageable object
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdDateTime").descending()
        );

        Page<BookTransaction> transactionsPageForBooksOfThisUser = bookTransactionRepository.findAllTransactionsRelatedToBooksOwnedByThisUser(pageable, user.getId());

        List<BorrowedBookResponseBody> borrowedBookResponse = transactionsPageForBooksOfThisUser.stream()
                .map(bookMapper::toBorrowedBookResponseBody)
                .toList();

        return new PageResponse<>(
                borrowedBookResponse,
                transactionsPageForBooksOfThisUser.getNumber(),  // current page number
                transactionsPageForBooksOfThisUser.getSize(),  // size of the page
                transactionsPageForBooksOfThisUser.getTotalElements(),
                transactionsPageForBooksOfThisUser.getTotalPages(),
                transactionsPageForBooksOfThisUser.isFirst(),  // whether the current page is the first page
                transactionsPageForBooksOfThisUser.isLast()
        );

    }

    // ***************************************************************************************************************

    //    TODO: check how to reduce code duplication for updating shareable & archived status
    public void updateShareableStatus(Integer bookId, Authentication authentication) {

        // check whether the book exists according to given bookId
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("The book with id " + bookId + " not found"));


        // check whether the targeted book is indeed owned by this user
        User user = (User) authentication.getPrincipal();

        if (!user.getId().equals(book.getOwner().getId())) {
            throw new IllegalOperationPerformException("You are not allowed to update someone else's book");
        }
        // update & save back to the db
        book.setIsShareable(!book.getIsShareable());

        bookRepository.save(book);
    }


    public void updateArchivedStatus(Integer bookId, Authentication authentication) {
        // check whether the book exists according to given bookId
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("The book with id " + bookId + " not found"));


        // check whether the targeted book is indeed owned by this user
        User user = (User) authentication.getPrincipal();

        if (!user.getId().equals(book.getOwner().getId())) {
            throw new IllegalOperationPerformException("You are not allowed to update someone else's book");
        }
        // update & save back to the db
        book.setIsArchived(!book.getIsArchived());

        bookRepository.save(book);
    }


    public void borrowABook(Integer bookId, Authentication authentication) {

        // check whether the bookId is valid
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("The book with id " + bookId + " not found"));

        // check whether the targeted book's owner is current user
        User user = (User) authentication.getPrincipal();

        if (user.getId().equals(book.getOwner().getId())) {
            throw new IllegalOperationPerformException("You can not borrow your own book !");
        }


        // check whether the book is archived / shareable
        if (book.getIsArchived() || !book.getIsShareable()) {
            throw new IllegalOperationPerformException("The book you asked is either archived or currently set as not shareable by owner. Please try again later. ");
        }

        // check whether the book is already borrowed by this particular user and not returned yet or the return not approved yet by the owner
        if (bookTransactionRepository.isTheBookAlreadyBorrowedByCurrUserAndNotReturned(bookId, user.getId())) {
            throw new IllegalOperationPerformException("You have not returned the book yet, or the return has not yet been approved by the book owner.");
        }

        // check whether the book is borrowed by some other user
        if (bookTransactionRepository.isTheBookAlreadyBorrowedBySomeOtherAndNotReturned(bookId, user.getId())) {
            throw new IllegalOperationPerformException("This book is already borrowed by someone else. Try again later ");
        }

        // save the transaction details to db
        BookTransaction bookTransaction = BookTransaction.builder()
                .isReturned(false)
                .isReturnApproved(false)
                .book(book)
                .user(user)
                .build();

        bookTransactionRepository.save(bookTransaction);
    }


    public void returnTheBorrowedBook(Integer bookId, Authentication authentication) {

        // check whether the bookId is valid
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("The book with id " + bookId + " not found"));


        // check whether the targeted book's owner is current user
        User user = (User) authentication.getPrincipal();

        // check whether the targeted book's owner is current user
        if (user.getId().equals(book.getOwner().getId())) {
            throw new IllegalOperationPerformException("You can not return your own book !");
        }

        // check whether the user has borrowed the book
        BookTransaction bookTransaction = bookTransactionRepository.findTheBookBorrowedByCurrUserAndNotReturned(
                bookId, user.getId()
        ).orElseThrow(
                () -> new IllegalOperationPerformException("You can not return this book, since you have not borrowed the book in the first place."));



        // set returned to true & save to db
        bookTransaction.setIsReturned(true);
        bookTransactionRepository.save(bookTransaction);

    }
}
