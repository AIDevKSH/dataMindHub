package com.datamindhub.blog.config;

import com.datamindhub.blog.config.Filter.CsrfLoggerFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

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
                .cors(c -> c
                        .configurationSource(CustomCorsConfig)
                )  // CORS 허용 커스텀 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(antMatcher("/login"), antMatcher("/signup")
                                , antMatcher("/users"), antMatcher("/users/id-check"))
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )  // 어느 경로를 인증받지 않고 사용할 수 있는지 설정
                .formLogin(login -> login
                        .loginPage("/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login")
                )  // 로그인 페이지와 로그인 관련 설정
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                )  // 로그아웃 관련 설정
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
