package com.datamindhub.blog.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;

//    public enum AuthorityEnum {
//        CREATE, READ, UPDATE, DELETE;
//
//        @Override
//        public String toString() {
//            return name().toLowerCase();
//        }
//    }
}
