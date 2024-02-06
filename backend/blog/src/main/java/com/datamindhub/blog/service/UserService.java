package com.datamindhub.blog.service;

import com.datamindhub.blog.domain.Role;
import com.datamindhub.blog.domain.User;
import com.datamindhub.blog.domain.UserRole;
import com.datamindhub.blog.dto.LoginRequestDto;
import com.datamindhub.blog.dto.UserRequestDto;
import com.datamindhub.blog.repository.AuthorityRepository;
import com.datamindhub.blog.repository.RoleRepository;
import com.datamindhub.blog.repository.UserRepository;
import com.datamindhub.blog.security.config.AuthenticationProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Month;


@Service
@RequiredArgsConstructor
@Slf4j
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
                .role(roleRepository.findByName("user").orElseThrow(
                        () -> new BadCredentialsException("해당 권한 정보가 없음"))
                )
                .build();
        user.getUserRoles().add(userRole);

        userRepository.save(user);  // cascade를 이용해서 user_authorities 테이블에도 저장
    }

    public String requestLogin(LoginRequestDto loginDto) {
        return authenticationProxy.requestLogin(loginDto)
                .orElseThrow(() -> new BadCredentialsException("로그인 정보 오류"));
    }

    public boolean checkIsDuplicatedUserEmail(String Email) {
        return userRepository.findByEmail(Email).isPresent();
    }
}
