package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.domain.Token;
import org.example.dto.TokenResponseDto;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenMapper {
    public TokenResponseDto toTokenResponseDto(Token token) {
        return new TokenResponseDto(token.getId(),
                token.getAccessToken(),
                token.getRefreshToken(),
                token.getAccessExpiresAt(),
                token.getRefreshExpiresAt());
    }
}
