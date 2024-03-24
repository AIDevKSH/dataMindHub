package com.datamindhub.blog.security.config;

import com.datamindhub.blog.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final CustomCorsConfig customCorsConfig;

    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers("/img/**", "/css/**", "/js/**");  // 정적 자원은 필터 무시
    }

    // 특정 Http 요청에 대한 보안 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // CSRF 테스트를 위한 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                // CORS 허용 커스텀 설정
                .cors(c -> c
                        .configurationSource(customCorsConfig)
                )

                //.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class)

                // httpBasic 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)

                // formLogin 비활성화
                .formLogin(FormLoginConfigurer::disable)

                // 세션 비활성화
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )

                // OAuth2 클라이언트 설정
                .oauth2Login(authConfig -> authConfig
                        .userInfoEndpoint(endpointConfig -> endpointConfig
                                .userService(customOAuth2UserService)
                        )
                        .loginPage("/login")  // 커스텀 로그인 페이지 설정
                        .defaultSuccessUrl("/", true)
                )

                // 어느 경로를 인증받지 않고 사용할 수 있는지 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(antMatcher("/login/**"), antMatcher("/oauth2/**"), antMatcher("/signup")
                                , antMatcher("/users"), antMatcher("/users/id-check"))
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
