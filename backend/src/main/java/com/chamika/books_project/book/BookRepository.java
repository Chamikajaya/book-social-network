package com.chamika.books_project.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer> {

    // Will not display the books which are archived, not shareable and the books which are owned by the current user
    @Query(
            """
            SELECT book
            FROM Book book
            WHERE book.isArchived = false
            AND book.isShareable = true
            AND book.owner.id != :userId
            """
    )
    Page<Book> findAllDisplayableBooks(Pageable pageable, Integer userId);  // * Spring Data JPA uses the Pageable object internally to construct the appropriate SQL query for pagination, which includes applying the LIMIT and OFFSET clauses based on the page number and page size. When we pass the Pageable object to the query method, Spring Data JPA handles the pagination logic behind the scenes
}
