package com.datamindhub.blog.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AuthenticationProxy {
    private final RestTemplate restTemplate;
    public void checkTokenIsValid(String token) {
        String url = "http://localhost:8085" + "/login/auth";

        HttpEntity<String> request = new HttpEntity<>(token);
        restTemplate.postForEntity(url, request, Void.class);
    }
}
