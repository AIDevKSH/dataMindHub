package com.datamindhub.auth.controller;

import com.datamindhub.auth.dto.UserRequestDto;
import com.datamindhub.auth.service.TokenService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/token/check")
    public ResponseEntity<String> checkIsValidToken(@RequestBody String token) throws BadCredentialsException {
        try {
            String email = tokenService.isValidToken(token);
            return ResponseEntity.ok().body(email);  // 예외가 발생하지 않으면 200 OK로 지정
        } catch (JwtException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  // 유저를 찾지 못했을 때
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDto userRequestDto) {
        try {
            return ResponseEntity.ok().body(
                    tokenService.generateToken(userRequestDto)
            );
        } catch (BadCredentialsException e) {  // 유저를 찾지 못했을 때
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }
}
