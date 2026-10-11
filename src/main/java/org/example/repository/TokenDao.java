package org.example.repository;

import org.example.domain.Token;
import org.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenDao extends JpaRepository<Token, Long> {
    Token findByRefreshToken(String refreshToken);
    Token findByUser(User user);
    Token findByAccessToken(String accessToken);
}
