package com.datamindhub.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    private final CustomCorsConfig CustomCorsConfig;

    // 특정 Http 요청에 대한 보안 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                //.addFilterAfter(new CsrfLoggerFilter(), CsrfFilter.class)
                .csrf(c -> c.disable())
                .cors(c -> c
                        .configurationSource(CustomCorsConfig)
                )  // CORS 허용 커스텀 설정
                .authorizeHttpRequests(auth -> auth
                        .anyRequest()
                        .permitAll()
                        //.authenticated()
                )  // 어떤 요청이든 인증을 받아야 함 - 일단 비활성화
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
