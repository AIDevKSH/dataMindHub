package com.datamindhub.blog.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_roles")
public class UserRole extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //@Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    //@Id
    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
