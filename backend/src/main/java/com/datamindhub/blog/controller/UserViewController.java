package com.datamindhub.blog.controller;

import com.datamindhub.blog.domain.Authority;
import com.datamindhub.blog.domain.User;
import com.datamindhub.blog.dto.UserRequestDto;
import com.datamindhub.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
public class UserViewController {

    @GetMapping("/login")
    public String login(Authentication auth) {  // 익명 사용자는 자동 처리 안 됨
        if (auth instanceof UsernamePasswordAuthenticationToken) {  // 사용자가 이미 로그인한 경우
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new UserRequestDto());
        return "/signup";
    }
}
