package com.datamindhub.blog.config;

import com.datamindhub.blog.dto.UserLoginAuthDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserNameAuthProvider implements AuthenticationProvider {
    private final AuthenticationProxy authenticationProxy;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        UserLoginAuthDto userLoginAuthDto = UserLoginAuthDto.builder()
                .email(email)
                .password(password)
                .build();
        try {
            authenticationProxy.LoginAuth(userLoginAuthDto);  // 인증 서버로 검증하도록 요청을 보냄
        } catch (RestClientException e) {
            throw new UsernameNotFoundException("유저 인증 실패");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
