package com.datamindhub.blog.service;

import com.datamindhub.blog.domain.user.RoleEnum;
import com.datamindhub.blog.domain.user.User;
import com.datamindhub.blog.domain.user.UserRole;
import com.datamindhub.blog.dto.LoginRequestDto;
import com.datamindhub.blog.dto.UserRequestDto;
import com.datamindhub.blog.repository.user.RoleRepository;
import com.datamindhub.blog.repository.user.UserRepository;
import com.datamindhub.blog.security.config.AuthenticationProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProxy authenticationProxy;

    public void save(UserRequestDto userDto) throws DataIntegrityViolationException {
        if (checkIsDuplicatedUserEmail(userDto.getEmail())) {
            throw new DataIntegrityViolationException("중복 아이디 발견");
        }

        // 계정 설정
        userDto.setStatus(1);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userDto.toEntity();

        // 역할 설정
        UserRole userRole = UserRole.builder()
                .user(user)
                .role(roleRepository.findByRoleEnum(RoleEnum.ROLE_USER).orElseThrow(
                        () -> new BadCredentialsException("해당 권한 정보가 없음"))
                )
                .build();

        user.setUserRole(userRole);

        userRepository.save(user);  // cascade를 이용해서 user_authorities 테이블에도 저장
    }

    public String requestLogin(LoginRequestDto loginDto) {
        return authenticationProxy.requestLogin(loginDto)
                .orElseThrow(() -> new BadCredentialsException("로그인 정보 오류"));
    }

    public Optional<User> findByEmail(String email) { return userRepository.findByEmail(email); }
    public Optional<User> findByProviderId(String providerId) { return userRepository.findByProviderId(providerId); }

    public boolean checkIsDuplicatedUserEmail(String Email) {
        return userRepository.findByEmail(Email).isPresent();
    }
}
