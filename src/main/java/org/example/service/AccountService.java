package org.example.service;

import org.example.dto.TokenResponseDto;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;

public interface AccountService {
    TokenResponseDto signIn(UserRequestDto requestDto);
    TokenResponseDto refresh(String refreshToken);
    void logOut(String refreshToken);
    UserResponseDto checkToken(String accessToken);
}
