package com.datamindhub.blog.repository;

import com.datamindhub.blog.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<User> findByEmail(String email) {
        String jpql = "SELECT u FROM User u LEFT JOIN FETCH u.userRole WHERE u.email = :email";
        return findOne(jpql, "email", email);
    }

    @Override
    public Optional<User> findByProviderId(String providerId) {
        String jpql = "SELECT u FROM User u LEFT JOIN FETCH u.userRole WHERE u.providerId = :providerId";
        return findOne(jpql, "providerId", providerId);
    }

    public Optional<User> findOne(String jpql, String col, String value) {
        User user = entityManager.createQuery(jpql, User.class)
                .setParameter(col, value)
                .getSingleResult();
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        String jpql = "SELECT u FROM User u LEFT JOIN FETCH u.userRole";
        return entityManager.createQuery(jpql, User.class).getResultList();
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }
}
