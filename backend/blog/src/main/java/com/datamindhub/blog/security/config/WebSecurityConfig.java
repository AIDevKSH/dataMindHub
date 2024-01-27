package com.datamindhub.blog.security.config;

import com.datamindhub.blog.security.Filter.CustomJwtFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    private final CustomJwtFilter customJwtFilter;
    private final CustomCorsConfig CustomCorsConfig;

    // 특정 Http 요청에 대한 보안 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
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
                .httpBasic(AbstractHttpConfigurer::disable)  // httpBasic 비활성화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .formLogin(login -> login
//                        .loginPage("/login")
//                        .usernameParameter("email")
//                        .defaultSuccessUrl("/", true)
//                        .failureUrl("/login")
//                )  // 로그인 페이지와 로그인 관련 설정
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/login")
//                        .invalidateHttpSession(true)
//                )  // 로그아웃 관련 설정
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
