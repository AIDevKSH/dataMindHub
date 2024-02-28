package com.datamindhub.blog.repository.user.impl;

import com.datamindhub.blog.domain.user.User;
import com.datamindhub.blog.repository.user.UserRepository;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class UserRepositoryImplTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("이메일 단일 조회")
    public void findEmail() {
        // given
        String email = "67b1e01c-08e8-4a05-b643-a24ac8f17d90@n";

        // when
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoResultException("유저 없음"));

        // then
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("모든 회원 조회")
    public void findAll() {
        // given
        int size = userRepository.findAll().size();

        // when
        User user = User.builder().email("new").build();
        userRepository.save(user);

        // then
        assertThat(userRepository.findAll().size()).isEqualTo(size + 1);
    }

    @Test
    @DisplayName("회원 저장")
    public void save() {
        // given
        String email = "new";
        User user = User.builder().email(email).build();

        // when
        userRepository.save(user);
        User foundUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoResultException("유저 없음"));

        // then
        assertThat(user).isEqualTo(foundUser);
    }

//    @TestConfiguration
//    static class config {
//        @Autowired EntityManager entityManager;
//
//        @Bean
//        public UserRepository userRepository() {
//            return new UserRepositoryImpl(entityManager);
//        }
//    }
}