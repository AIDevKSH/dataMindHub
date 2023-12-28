package com.datamindhub.blog.service;

import com.datamindhub.blog.domain.User;
import com.datamindhub.blog.dto.UserRequestDto;
import com.datamindhub.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(UserRequestDto userDto) throws DataIntegrityViolationException {
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new DataIntegrityViolationException("중복 아이디 발견");
        }
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        return userRepository.save(userDto.toEntity()).getId();
    }

    public boolean checkIsDuplicatedUserEmail(String Email) {
        if (userRepository.findByEmail(Email) != null) {
            return true;
        }
        else return false;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return user;
    }
}
