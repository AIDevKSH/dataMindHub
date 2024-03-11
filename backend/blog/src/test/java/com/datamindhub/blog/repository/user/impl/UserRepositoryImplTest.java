package com.datamindhub.blog.repository.user.impl;

import com.datamindhub.blog.domain.user.*;
import com.datamindhub.blog.repository.user.UserRepository;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class UserRepositoryImplTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager entityManager;

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

    @Test
    @DisplayName("NamedEntityGraph로 UserRole 조회 성공")
    public void namedEntityGraphTest() {
        // given
        Role role = Role.builder().name(RoleEnum.ROLE_USER).build();
        User user = User.builder().email("테스트").build();
        UserRole userRole = UserRole.builder().user(user).role(role).build();

        entityManager.persist(userRole);
        entityManager.flush();
        entityManager.clear();

        // when
        EntityGraph<?> withUserRole = entityManager.getEntityGraph("withUserRole");
        Map<String, Object> hints = Map.of("javax.persistence.fetchgraph", withUserRole);
        User foundUser = entityManager.find(User.class, user.getId(), hints);

        // then
        boolean loaded = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(foundUser.getUserRole());
        Assertions.assertThat(loaded).isTrue();
    }

    @Test
    @DisplayName("subgraph로 role 조회 성공")
    public void subgraphTest() {
        // given
        Role role = Role.builder().name(RoleEnum.ROLE_USER).build();
        User user = User.builder().email("테스트").build();
        UserRole userRole = UserRole.builder().user(user).role(role).build();

        entityManager.persist(userRole);
        entityManager.flush();
        entityManager.clear();

        // when
        EntityGraph<?> withRole = entityManager.getEntityGraph("withRole");
        Map<String, Object> hints = Map.of("javax.persistence.fetchgraph", withRole);
        User foundUser = entityManager.find(User.class, user.getId(), hints);

        // then
        boolean loaded = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(foundUser.getUserRole());
        Assertions.assertThat(loaded).isTrue();

        loaded = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(foundUser.getUserRole().getRole());
        Assertions.assertThat(loaded).isTrue();
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