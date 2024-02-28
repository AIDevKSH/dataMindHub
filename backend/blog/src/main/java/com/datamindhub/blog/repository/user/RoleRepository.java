package com.datamindhub.blog.repository.user;

import com.datamindhub.blog.domain.user.Role;
import com.datamindhub.blog.domain.user.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByRoleEnum(RoleEnum name);
}
