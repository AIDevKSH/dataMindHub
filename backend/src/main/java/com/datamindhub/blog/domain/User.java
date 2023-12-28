package com.datamindhub.blog.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
@Table(name = "Users")
public class User extends BaseDateTimeEntity implements UserDetails  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_name", length = 30, nullable = false)
    private String userName;

    @Column(name = "birthday", length = 9, nullable = true)  // 양력(0), 음력(1) + xxxx.xx.xx
    private String birthday;

    @Column(name = "nickname", length = 20, nullable = false)
    private String nickname;

    @Column(name = "phone", length = 15, nullable = false)
    private String phone;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "status", nullable = false)
    private int status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    // 사용자 아이디
    @Override
    public String getUsername() {
        return email;
    }

    // 사용자 비밀번호
    @Override
    public String getPassword() {
        return password;
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
