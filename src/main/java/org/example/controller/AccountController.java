package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.TokenResponseDto;
import org.example.dto.UserRequestDto;
import org.example.dto.UserResponseDto;
import org.example.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountService accountService;

    @PostMapping("/sign-in")
    public TokenResponseDto signIn(@RequestBody UserRequestDto requestDto){
        return accountService.signIn(requestDto);
    }

    @PostMapping("/refresh")
    public TokenResponseDto refresh(@RequestParam String refreshToken){
        return accountService.refresh(refreshToken);
    }

    @PostMapping("/log-out")
    public void logOut (@RequestParam String refreshToken){
        accountService.logOut(refreshToken);
    }

    @GetMapping
    public UserResponseDto checkToken(@RequestParam String accessToken){
        return accountService.checkToken(accessToken);
    }



}
