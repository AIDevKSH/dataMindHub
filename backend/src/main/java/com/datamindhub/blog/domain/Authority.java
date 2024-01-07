package com.datamindhub.blog.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Integer id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;

    public enum AuthorityEnum {
        CREATE, READ, UPDATE, DELETE;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}
