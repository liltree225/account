package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.domain.Token;
import org.example.domain.User;
import org.example.dto.TokenResponseDto;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.mapper.TokenMapper;
import org.example.mapper.UserMapper;
import org.example.repository.TokenDao;
import org.example.repository.UserDao;
import org.example.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserDao userDao;
    private final TokenDao tokenDao;
    private final TokenMapper tokenMapper;
    private final UserMapper userMapper;
    @Transactional
    @Override
    public TokenResponseDto signIn(UserRequestDto requestDto) {
        User user = userDao.findByEmail(requestDto.getEmail());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user с email не найден");
        }

        if (!Objects.equals(user.getPassword(), requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "пароли не совпадают");
        }
//todo privatmethod
        Token token = new Token();
        token.setUser(user);
        token.setAccessToken(UUID.randomUUID());
        token.setRefreshToken(UUID.randomUUID());
        token.setAccessExpiresAt(LocalDateTime.now().plusHours(1));
        token.setRefreshExpiresAt(LocalDateTime.now().plusHours(24));
        tokenDao.save(token);
        return tokenMapper.toTokenResponseDto(token);
    }

    @Transactional
    @Override
    public TokenResponseDto refresh(String refreshToken) {
        Token token = tokenDao.findByRefreshToken(refreshToken);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "token не действителен");
        }
        if (token.getRefreshExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "token не действителен");
        }
        token.setAccessToken(UUID.randomUUID());
        token.setAccessExpiresAt(LocalDateTime.now().plusHours(1));
        tokenDao.save(token);

        return tokenMapper.toTokenResponseDto(token);
    }

    @Transactional
    @Override
    public void logOut(String refreshToken) {

        Token token = tokenDao.findByRefreshToken(refreshToken);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "token не действителен");
        }
        tokenDao.delete(token);

    }

    @Transactional
    @Override
    public UserResponseDto checkToken(String accessToken) {
        Token token = tokenDao.findByAccessToken(accessToken);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "token не действителен");
        }
        if (token.getAccessExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "token не действителен");
        }
        User user = token.getUser();

        return userMapper.toUserResponseDto(user);
    }


}
