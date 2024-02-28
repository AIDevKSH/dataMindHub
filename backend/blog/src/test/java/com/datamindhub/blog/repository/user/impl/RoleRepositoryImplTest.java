package com.datamindhub.blog.repository.user.impl;

import com.datamindhub.blog.domain.user.Role;
import com.datamindhub.blog.domain.user.RoleEnum;
import com.datamindhub.blog.repository.user.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class RoleRepositoryImplTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("Role 이름으로 조회")
    public void findByRoleEnum() {
        // given
        RoleEnum roleEnum = RoleEnum.ROLE_ADMIN;

        // when
        Optional<Role> foundRole = roleRepository.findByRoleEnum(roleEnum);

        // then
        Assertions.assertThat(foundRole.isPresent()).isTrue();
    }
}