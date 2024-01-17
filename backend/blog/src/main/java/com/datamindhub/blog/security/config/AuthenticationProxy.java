package com.datamindhub.blog.security.config;

import com.datamindhub.blog.dto.UserLoginAuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AuthenticationProxy {
    private final RestTemplate restTemplate;
    public void LoginAuth(UserLoginAuthDto userLoginAuthDto) {
        String url = "http://localhost:8085" + "/login/auth";

        HttpEntity<UserLoginAuthDto> request = new HttpEntity<>(userLoginAuthDto);
        restTemplate.postForEntity(url, request, Void.class);
    }
}
