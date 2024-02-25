package com.datamindhub.blog.controller;

import com.datamindhub.blog.domain.CustomOAuth2User;
import com.datamindhub.blog.dto.post.PostDto;
import com.datamindhub.blog.service.UserService;
import com.datamindhub.blog.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/")
    public String viewArticles() {
        return "/post/post-list";
    }

    @PostMapping("/post/save")
    public String save(@ModelAttribute PostDto postDto, Authentication auth) {
        CustomOAuth2User user = (CustomOAuth2User) auth.getPrincipal();
        log.info(user.getId());
        Long userId = userService.findByProviderId(user.getId()).orElseThrow().getId();
        postDto.setUserId(userId);
        postService.save(postDto);
        return "redirect:/";
    }
}
