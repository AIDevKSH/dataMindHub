package com.datamindhub.blog.domain;

import com.datamindhub.blog.domain.Role;
import com.datamindhub.blog.domain.User;
import com.datamindhub.blog.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Role role = user.getUserRole().getRole();

        if (role == null) throw new UsernameNotFoundException("No Roles");

        ArrayList<SimpleGrantedAuthority> authorities = role.getAuthorities().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName().toUpperCase()))
                .collect(Collectors.toCollection(ArrayList::new));
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
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
