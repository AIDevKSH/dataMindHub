package com.datamindhub.auth.service;

import com.datamindhub.auth.domain.User;
import com.datamindhub.auth.dto.UserLoginAuthDto;
import com.datamindhub.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void auth(UserLoginAuthDto userLoginAuthDto) {
        Optional<User> user = userRepository.findByEmail(userLoginAuthDto.getEmail());

        if (user.isPresent()) {  // 유저가 있을 때
            if (passwordEncoder.matches(userLoginAuthDto.getPassword(), user.get().getPassword())) {
                return;
            }
        }
        // 유저가 없거나 비밀번호가 틀렸을 때
        throw new BadCredentialsException("유저 인증 실패");
    }
}
