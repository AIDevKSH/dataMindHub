package com.datamindhub.blog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {
    @GetMapping("/")
    public String viewArticles() {
        return "/post/post-list";
    }
}
