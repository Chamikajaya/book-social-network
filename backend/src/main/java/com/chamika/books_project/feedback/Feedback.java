package com.chamika.books_project.feedback;


import com.chamika.books_project.book.Book;
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

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_feedback")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_id_sequence")
    @SequenceGenerator(name = "feedback_id_sequence", sequenceName = "feedback_id_sequence", allocationSize = 1)
    private Integer id;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)  // * for the relationship between book and feedback, Feedback entity is the owning side ??
    private Book book;


    // audit fields -->
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Integer createdBy;

    @LastModifiedBy
    @Column(insertable = false)
    private Integer lastModifiedBy;
}
