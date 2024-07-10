package com.chamika.books_project.token;

import com.chamika.books_project.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_token")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_id_sequence")
    @SequenceGenerator(name = "token_id_sequence", sequenceName = "token_id_sequence", allocationSize = 1)
    private Integer id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private LocalDateTime validatedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")  // the foreign key column name
    private User user;




}
