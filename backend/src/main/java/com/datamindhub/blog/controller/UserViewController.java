package com.datamindhub.blog.controller;

import com.datamindhub.blog.domain.User;
import com.datamindhub.blog.domain.UserAuthority;
import com.datamindhub.blog.dto.UserRequestDto;
import com.datamindhub.blog.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserViewController {
    private final UserRepository userRepository;
    @GetMapping("/login")
    public String login(Authentication auth) {
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
