package com.example.userservicemwf.repository;

import com.example.userservicemwf.models.Token;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Configuration
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token save(Token token);

    Optional<Token> findByValueAndDeleted(String values, boolean deleted);
}
