package com.datamindhub.blog.repository.user;

import com.datamindhub.blog.domain.user.Authority;
import com.datamindhub.blog.domain.user.AuthorityEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface AuthorityRepository {
    Optional<Authority> findByAuthorityEnum(AuthorityEnum authorityEnum);
}
