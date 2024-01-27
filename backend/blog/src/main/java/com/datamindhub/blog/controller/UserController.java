package com.datamindhub.blog.controller;

import com.datamindhub.blog.dto.LoginRequestDto;
import com.datamindhub.blog.dto.UserRequestDto;
import com.datamindhub.blog.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    public String loginView(Authentication auth) {  // 익명 사용자는 자동 주입 처리 안 되는 것 주의!
        if (auth instanceof UsernamePasswordAuthenticationToken) {  // 사용자가 이미 로그인한 경우
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@ModelAttribute LoginRequestDto loginDto) {
        String token = null;

        if (!loginDto.getEmail().isEmpty() && !loginDto.getPassword().isEmpty()) {  // 이메일, 비밀번호 둘 다 있을 때
            try {
                token = userService.requestLogin(loginDto);

                return ResponseEntity.ok()
                        .header("Authorization", "Bearer " + token)
                        .body("");
            } catch (BadCredentialsException e) {
                log.error("로그인 오류", e);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 오류");
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