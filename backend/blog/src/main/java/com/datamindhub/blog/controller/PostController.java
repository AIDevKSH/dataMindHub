package com.datamindhub.blog.controller;

import com.datamindhub.blog.domain.post.Post;
import com.datamindhub.blog.domain.user.CustomOAuth2User;
import com.datamindhub.blog.domain.user.User;
import com.datamindhub.blog.dto.post.PostDto;
import com.datamindhub.blog.service.UserService;
import com.datamindhub.blog.service.post.PostService;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/")
    public String getPosts(Authentication auth , Model model) {
        CustomOAuth2User userAuth = (CustomOAuth2User) auth.getPrincipal();
        Optional<User> user = userService.findByProviderId(userAuth.getId());

        if (user.isPresent()) {
            List<Post> posts = postService.findByUserId(user.get().getId());
            model.addAttribute("posts", posts);
        }
        return "post/post-list";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable String id, Model model) {
        Optional<Post> post = postService.findById(Long.valueOf(id));

        if (post.isPresent()) {
            model.addAttribute("post", post.get());
        } else log.info("n");
        return "post/post";
    }

    @GetMapping("/new-post")
    public String newPost() {
        return "/post/new-post";
    }

    @PostMapping("/new-post")
    public String newPost(@ModelAttribute PostDto postDto, Authentication auth) {
        CustomOAuth2User user = (CustomOAuth2User) auth.getPrincipal();
        log.info(user.getId());
        Long userId = userService.findByProviderId(user.getId()).orElseThrow().getId();
        postDto.setUserId(userId);
        postDto.setStatus((byte) 1);
        postService.save(postDto);
        return "redirect:/";
    }
}
