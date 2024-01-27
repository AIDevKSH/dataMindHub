package com.datamindhub.auth.service;

import com.datamindhub.auth.config.JwtTokenProvider;
import com.datamindhub.auth.domain.User;
import com.datamindhub.auth.dto.UserRequestDto;
import com.datamindhub.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public void auth(UserRequestDto userRequestDto) {
        Optional<User> user = userRepository.findByEmail(userRequestDto.getEmail());

        if (user.isPresent()) {  // 유저가 있을 때
            if (passwordEncoder.matches(userRequestDto.getPassword(), user.get().getPassword())) {
                return;
            }
        }
        // 유저가 없거나 비밀번호가 틀렸을 때
        throw new BadCredentialsException("유저 인증 실패");
    }

    public String makeToken(UserRequestDto userRequestDto) {
        auth(userRequestDto);  // 사용자 인증
        return tokenProvider.makeDefaultToken(userRequestDto);
    }
}
