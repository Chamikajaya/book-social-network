package com.chamika.books_project.book;

import com.chamika.books_project.feedback.Feedback;
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

    private Boolean isArchived = false;  // Soft delete

    private Boolean isShareable = true;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    // * for the relationship between book and user, Book entity is the owning side 😊
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "book")
    private List<BookTransaction> transactions;


    // audit fields -->
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Integer createdBy;  // user id of the user who created the book

    @LastModifiedBy
    @Column(insertable = false)
    private Integer lastModifiedBy;  // user id of the user who last modified the book

    @Transient  // to indicate that a field is not to be persisted in the database
    public double getAverageRating() {

        if (feedbacks == null || feedbacks.isEmpty()) {
            return 0;
        }

        double average = feedbacks.stream().mapToDouble(Feedback::getRating).average().orElse(0);
        return Math.round(average * 10) / 10.0;  // round to 1 decimal place


    }
}
