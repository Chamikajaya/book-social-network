package com.chamika.books_project.transactions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookTransactionRepository extends JpaRepository<BookTransaction, Integer> {


    @Query(
            """
            SELECT bookTransaction
            FROM BookTransaction bookTransaction
            WHERE bookTransaction.user.id = :userId
            """
    )
    Page<BookTransaction> findAllTransactionsRelatedToThisUser(Pageable pageable, Integer userId);


    @Query(
            """
            SELECT bookTransaction
            FROM BookTransaction bookTransaction
            WHERE bookTransaction.book.owner.id = :userId
            """
    )
    Page<BookTransaction> findAllTransactionsRelatedToBooksOwnedByThisUser(Pageable pageable, Integer userId);


    @Query(
            """
            SELECT CASE WHEN COUNT(bookTransaction) > 0 THEN true ELSE false END
            FROM BookTransaction bookTransaction
            WHERE bookTransaction.book.id = :bookId
            AND bookTransaction.user.id = :userId
            AND (bookTransaction.isReturned = false
            OR bookTransaction.isReturnApproved = false)
            """
    )
    Boolean isTheBookAlreadyBorrowedByCurrUserAndNotReturned(Integer bookId, Integer userId);

    @Query(
            """
            SELECT CASE WHEN COUNT(bookTransaction) > 0 THEN true ELSE false END
            FROM BookTransaction bookTransaction
            WHERE bookTransaction.book.id = :bookId
            AND bookTransaction.user.id != :userId
            AND (bookTransaction.isReturned = false
            OR bookTransaction.isReturnApproved = false)
            """
    )
    Boolean isTheBookAlreadyBorrowedBySomeOtherAndNotReturned(Integer bookId, Integer userId);

    @Query(
            """
            SELECT bookTransaction
            FROM BookTransaction bookTransaction
            WHERE bookTransaction.book.id = :bookId
            AND bookTransaction.user.id = :userId
            AND bookTransaction.isReturned = false
            """
    )
    Optional<BookTransaction> findTheBookBorrowedByCurrUserAndNotReturned(Integer bookId, Integer userId);







}
