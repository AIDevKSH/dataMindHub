package com.datamindhub.auth.controller;

import com.datamindhub.auth.dto.UserLoginAuthDto;
import com.datamindhub.auth.service.LoginAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginAuthController {
    private final LoginAuthService loginAuthService;

    @PostMapping("/login/auth")
    public void loginAuthCheck(@RequestBody UserLoginAuthDto userLoginAuthDto, HttpServletResponse response)
            throws BadCredentialsException {
        loginAuthService.auth(userLoginAuthDto);
        response.setStatus(HttpServletResponse.SC_OK);  // 예외가 발생하지 않으면 200 OK로 지정
    }
}
