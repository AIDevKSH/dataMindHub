package com.datamindhub.blog.domain.user;

import com.datamindhub.blog.domain.BaseDateTimeEntity;
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
public class User extends BaseDateTimeEntity {
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
}
