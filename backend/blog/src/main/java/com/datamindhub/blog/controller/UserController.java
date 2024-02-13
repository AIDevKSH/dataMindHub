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

//    @ResponseBody
//    @GetMapping("/test")
//    public String test() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        log.info("{}", auth.getClass());
//        return "good";
//    }

    @GetMapping("/login")
    public String loginView(OAuth2AuthenticationToken token) {  // 익명 사용자는 자동 주입 처리 안 되는 것 주의!
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth instanceof OAuth2AuthenticationToken) {
//            return "redirect:/";
//        }
        //log.info("{}", auth.getClass());
//        if (token != null) {  // 사용자가 이미 로그인한 경우
//            return "redirect:/";
//        }
        return "/login";
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@ModelAttribute LoginRequestDto loginDto) {
//        String token = null;
//
//        if (!loginDto.getEmail().isEmpty() && !loginDto.getPassword().isEmpty()) {  // 이메일, 비밀번호 둘 다 있을 때
//            try {
//                token = userService.requestLogin(loginDto);
//
//                return ResponseEntity.ok()
//                        .header("Authorization", "Bearer " + token)
//                        .body("");
//            } catch (BadCredentialsException e) {
//                log.error("로그인 오류", e);
//            }
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 오류");
//    }

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