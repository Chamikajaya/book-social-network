package com.chamika.books_project.token;

import com.chamika.books_project.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);  // find the token object by the token string
}
