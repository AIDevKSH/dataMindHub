package com.datamindhub.blog.security.Filter;

import com.datamindhub.blog.security.CustomUserDetailsService;
import com.datamindhub.blog.security.config.AuthenticationProxy;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomJwtFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationProxy authenticationProxy;
    private final static String HEADER = "Authorization";
    private final static String PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestHeader = request.getHeader(HEADER);  // 헤더에 있는 Authorization 값을 가져옴

        if (requestHeader != null && requestHeader.startsWith(PREFIX)) {  // 헤더 정보가 있고, 시작이 PREFIX 일 때
            String token = requestHeader.substring(PREFIX.length());  // 토큰 정보만 가져옴

            try {
                String email = authenticationProxy.checkTokenIsValid(token);  // 인증 서버로 토큰 확인 요청
                UserDetails user = userDetailsService.loadUserByUsername(email);
                Authentication auth = new UsernamePasswordAuthenticationToken(  // 새 유저 인증 객체 생성
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
                doFilter(request, response, filterChain);
            } catch (RestClientException e) {
                log.debug("인증 정보 불일치");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        else {
            log.debug("제공된 정보 없음");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/login");
    }
}
