package com.datamindhub.blog.config;

import com.datamindhub.blog.domain.User;
import com.datamindhub.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        if (customUserDetails == null) {
            throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
        }
        return customUserDetails;
    }
}
