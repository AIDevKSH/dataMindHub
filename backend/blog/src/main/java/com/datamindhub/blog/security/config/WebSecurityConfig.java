package com.datamindhub.blog.security.config;

import com.datamindhub.blog.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    //private final CustomJwtFilter customJwtFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomCorsConfig CustomCorsConfig;

    // 특정 Http 요청에 대한 보안 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c
                        .configurationSource(CustomCorsConfig)
                )  // CORS 허용 커스텀 설정
                //.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(antMatcher("/login"), antMatcher("/signup")
                                , antMatcher("/users"), antMatcher("/users/id-check"))
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )  // 어느 경로를 인증받지 않고 사용할 수 있는지 설정
                .httpBasic(AbstractHttpConfigurer::disable)  // httpBasic 비활성화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // OAuth2 클라이언트 설정
                .oauth2Login(authConfig -> authConfig
                        .userInfoEndpoint(endpointConfig -> endpointConfig
                                .userService(customOAuth2UserService)
                        )
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
