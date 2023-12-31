package com.datamindhub.blog.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", length = 254, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_name", length = 30, nullable = false)
    private String userName;

    @Column(name = "birthday", length = 8)  // AAAABBCC
    private String birthday;

    @Column(name = "nickname", length = 50, nullable = false)
    private String nickname;

    @Column(name = "phone", length = 15, nullable = false)
    private String phone;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "status", nullable = false)
    private int status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<UserRole> userRoles;
}
