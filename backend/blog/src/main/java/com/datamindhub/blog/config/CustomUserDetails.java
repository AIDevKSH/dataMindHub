package com.datamindhub.blog.config;

import com.datamindhub.blog.domain.Role;
import com.datamindhub.blog.domain.User;
import com.datamindhub.blog.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = user.getUserRoles().stream()
                .map(UserRole::getRole)
                .findFirst().orElseThrow(() -> new UsernameNotFoundException("No Roles"));

        return role.getAuthorities().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .toList();
        //return List.of(new SimpleGrantedAuthority("user"));
    }

    // 사용자 아이디
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // 사용자 비밀번호
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용 가능한지 여부
    @Override
    public boolean isEnabled() {
        return true;
    }
}
