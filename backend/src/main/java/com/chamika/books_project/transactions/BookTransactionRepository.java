package com.chamika.books_project.transactions;

import com.chamika.books_project.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
            WHERE bookTransaction.book.owner.id = :userid
            """
    )
    Page<BookTransaction> findAllTransactionsRelatedToBooksOwnedByThisUser(Pageable pageable, Integer userId);

}
