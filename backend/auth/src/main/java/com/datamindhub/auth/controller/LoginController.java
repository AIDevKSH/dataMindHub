package com.datamindhub.auth.controller;

import com.datamindhub.auth.dto.UserRequestDto;
import com.datamindhub.auth.service.LoginAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginAuthService loginAuthService;

    @PostMapping("/login/auth")
    public void loginAuthCheck(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response)
            throws BadCredentialsException {
        loginAuthService.auth(userRequestDto);
        response.setStatus(HttpServletResponse.SC_OK);  // 예외가 발생하지 않으면 200 OK로 지정
    }

    @PostMapping("/login")
    public void login(@RequestBody UserRequestDto userRequestDto) {

    }
}
