package com.datamindhub.blog.domain.user;

import com.datamindhub.blog.domain.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NamedEntityGraphs(value = {
        // userRole
        @NamedEntityGraph(name = "withUserRole", attributeNodes = {
                @NamedAttributeNode("userRole")
        }),

        // role
        @NamedEntityGraph(name = "withRole", attributeNodes = {
                @NamedAttributeNode(value = "userRole", subgraph = "userRole")
        }, subgraphs = @NamedSubgraph(name = "userRole", attributeNodes = {
                @NamedAttributeNode("role")
        }))
})

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
    @Column(name = "id")
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "birthday")  // AAAABBCC
    private String birthday;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "status")
    private int status;

    @Column(name = "provider_id")
    private String providerId;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private UserRole userRole;
}
