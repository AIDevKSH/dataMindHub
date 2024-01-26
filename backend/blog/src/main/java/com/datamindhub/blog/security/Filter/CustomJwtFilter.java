package com.datamindhub.blog.security.Filter;

import com.datamindhub.blog.security.config.AuthenticationProxy;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomJwtFilter extends OncePerRequestFilter {
    private final AuthenticationProxy authenticationProxy;
    private final static String HEADER = "Authorization";
    private final static String PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestHeader = request.getHeader(HEADER);  // 헤더에 있는 Authorization 값을 가져옴

        if (requestHeader != null && requestHeader.length() > PREFIX.length()) {  // 헤더 정보가 있고, PREFIX 보다 길 때
            String token = requestHeader.substring(PREFIX.length());  // 토큰 정보만 가져옴

            try {
                authenticationProxy.checkTokenIsValid(token);  // 인증 서버로 토큰 확인 요청
                doFilter(request, response, filterChain);
                return;
            } catch (RestClientException e) {
                log.info("인증 정보 불일치");
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
