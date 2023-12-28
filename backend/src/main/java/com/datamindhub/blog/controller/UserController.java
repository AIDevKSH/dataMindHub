package com.datamindhub.blog.controller;

import com.datamindhub.blog.dto.UserRequestDto;
import com.datamindhub.blog.repository.UserRepository;
import com.datamindhub.blog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public String addUser(@Valid @ModelAttribute("user") UserRequestDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.debug("유저 등록 error");
            return "/signup";
        }
        try {
            userService.save(user);
        }
        catch (DataIntegrityViolationException e) {
            log.error("중복 이메일 발견으로 유저 가입 실패");
        }

        return "redirect:/login";
    }

    @ResponseBody
    @GetMapping("/id-check")
    public int checkUserId(@RequestParam String email) {
        return userService.checkIsDuplicatedUserEmail(email) ? 0 : 1;  // 참이면 중복(0), 거짓이면 중복 아님(1)
    }
}