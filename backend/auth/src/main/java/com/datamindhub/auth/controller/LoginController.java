package com.datamindhub.auth.controller;

import com.datamindhub.auth.dto.UserRequestDto;
import com.datamindhub.auth.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
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
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/token/check")
    public void loginAuthCheck(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response)
            throws BadCredentialsException {
        try {
            loginService.auth(userRequestDto);
            response.setStatus(HttpServletResponse.SC_OK);  // 예외가 발생하지 않으면 200 OK로 지정
        } catch (BadCredentialsException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 유저를 찾지 못했을 때
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRequestDto userRequestDto) {
        try {
            return ResponseEntity.ok().body(
                    loginService.makeToken(userRequestDto)
            );
        } catch (BadCredentialsException e) {  // 유저를 찾지 못했을 때
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }
}
