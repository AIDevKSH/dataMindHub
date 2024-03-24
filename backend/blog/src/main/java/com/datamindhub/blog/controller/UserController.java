package com.datamindhub.blog.controller;

import com.datamindhub.blog.dto.UserRequestDto;
import com.datamindhub.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("")
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String loginView() {  // 익명 사용자는 자동 주입 처리 안 되는 것 주의!
        return "/login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new UserRequestDto());
        return "/signup";
    }

    @PostMapping("/users")
    public String addUser(@Valid @ModelAttribute("user") UserRequestDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("유저 등록 error");
            return "/signup";
        }
        try {
            userService.save(userDto);
        }
        catch (DataIntegrityViolationException e) {
            log.error("중복 이메일 발견으로 유저 가입 실패");
        }
        return "redirect:/login";
    }

    @ResponseBody
    @GetMapping("/users/id-check")
    public int checkUserId(@RequestParam String email) {
        return userService.checkIsDuplicatedUserEmail(email) ? 1 : 0;  // 참이면 중복(1), 거짓이면 중복 아님(0)
    }
}