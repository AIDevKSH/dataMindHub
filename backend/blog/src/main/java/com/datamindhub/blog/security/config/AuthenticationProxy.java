package com.datamindhub.blog.security.config;

import com.datamindhub.blog.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationProxy {
    private final RestTemplate restTemplate;
    public String checkTokenIsValid(String token) {
        String url = "http://localhost:8085" + "/token/check";
        HttpEntity<String> request = new HttpEntity<>(token);

        HttpEntity<String> email = restTemplate.postForEntity(url, request, String.class);
        return email.getBody();  // 해당 유저 이메일 반환
    }

    public Optional<String> requestLogin(LoginRequestDto loginDto) {
        String url = "http://localhost:8085" + "/login";
        ResponseEntity<String> response;
        HttpEntity<LoginRequestDto> request = new HttpEntity<>(loginDto);

        try {
            response = restTemplate.postForEntity(url, request, String.class);
        } catch (RestClientException e) {
            return Optional.empty();
        }

        return Optional.ofNullable(response.getBody());  // 발급받은 토큰
    }
}
