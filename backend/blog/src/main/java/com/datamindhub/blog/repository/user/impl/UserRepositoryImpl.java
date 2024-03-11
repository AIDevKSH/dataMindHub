package com.datamindhub.blog.repository.user.impl;

import com.datamindhub.blog.domain.user.User;
import com.datamindhub.blog.repository.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String jpql = "SELECT u FROM User u LEFT JOIN FETCH u.userRole WHERE u.email = :email";
        return findOne(jpql, "email", email);
    }

    @Override
    public Optional<User> findByProviderId(String providerId) {
        String jpql = "SELECT u FROM User u LEFT JOIN FETCH u.userRole r WHERE u.providerId = :providerId";
        return findOne(jpql, "providerId", providerId);
    }

    public <T> Optional<User> findOne(String jpql, String col, T value) {
        User user = null;

        try {
            user = entityManager.createQuery(jpql, User.class)
                    .setParameter(col, value)
                    .getSingleResult();
        } catch (NoResultException e) {
            log.debug("유저 조회 실패", e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        String jpql = "SELECT u FROM User u LEFT JOIN FETCH u.userRole";
        return entityManager.createQuery(jpql, User.class).getResultList();
    }

    @Override
    public Long save(User user) {
        entityManager.persist(user);
        return user.getId();
    }
}
