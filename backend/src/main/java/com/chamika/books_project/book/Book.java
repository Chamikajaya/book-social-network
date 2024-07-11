package com.chamika.books_project.book;

import com.chamika.books_project.feedback.Feedback;
import com.chamika.books_project.role.Role;
import com.chamika.books_project.transactions.BookTransaction;
import com.chamika.books_project.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_book")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_sequence")
    @SequenceGenerator(name = "book_id_sequence", sequenceName = "book_id_sequence", allocationSize = 1)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String authorName;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String synopsis;

    private String coverImage;

    private Boolean isArchived = false;

    private Boolean isShareable = true;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "book")
    @JoinColumn(name = "feedback_id")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "book")
    @JoinColumn(name = "transaction_id")
    private List<BookTransaction> transactions;


    // audit fields -->
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDate createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Integer createdBy;  // user id of the user who created the book

    @LastModifiedBy
    @Column(insertable = false)
    private Integer lastModifiedBy;  // user id of the user who last modified the book


}
