package com.datamindhub.blog.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Byte id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "authority")
    private List<UserAuthority> authorities = new ArrayList<>();

    public enum AuthorityEnum {
        WRITE, READ;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
}
