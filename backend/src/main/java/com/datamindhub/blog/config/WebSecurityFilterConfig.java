package com.datamindhub.blog.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityFilterConfig {
    //private final UserDetailsService userService;

    // H2 콘솔과 정적 리소스에 대한 시큐리티 기능 비활성화
//    @Bean
//    @Order(1)
//    public SecurityFilterChain configureH2AndStaticFilterChain(HttpSecurity http) throws Exception {
//        return http.securityMatcher(toH2Console())
//                .authorizeHttpRequests(auth -> auth
//                    .requestMatchers(toH2Console(), PathRequest.toStaticResources().atCommonLocations())
//                        .permitAll()
//                )
//                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
//                .csrf(c->c.ignoringRequestMatchers(toH2Console()))
//                .build();
//    }

    // 특정 Http 요청에 대한 보안 설정
    //@Order(2)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(antMatcher("/login"), antMatcher("/signup"), antMatcher("/users"), antMatcher("/users/id-check"))
                        .permitAll()
                    .anyRequest()
                        .authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .usernameParameter("email")
                        .failureUrl("/login")
                        .successHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                                -> response.sendRedirect("/")
                        )
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }


    //인증 관리자 관련 설정
//    @Bean
//    public void authenticationManager(AuthenticationManagerBuilder auth, BCryptPasswordEncoder bCryptPasswordEncoder
//            , UserDetailsService userDetailsService) throws Exception {
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(bCryptPasswordEncoder);
//    }
}
