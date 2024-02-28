package com.datamindhub.blog.repository.user.impl;

import com.datamindhub.blog.domain.user.Authority;
import com.datamindhub.blog.domain.user.AuthorityEnum;
import com.datamindhub.blog.repository.user.AuthorityRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest
class AuthorityRepositoryImplTest {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Test
    @DisplayName("권한 이름으로 조회")
    public void findByAuthorityEnum() {
        // given
        AuthorityEnum authorityEnum = AuthorityEnum.CREATE;

        // when
        Optional<Authority> authority = authorityRepository.findByAuthorityEnum(authorityEnum);

        // then
        Assertions.assertThat(authority.isPresent()).isTrue();
    }
}