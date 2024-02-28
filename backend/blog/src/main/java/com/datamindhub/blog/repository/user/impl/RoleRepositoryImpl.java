package com.datamindhub.blog.repository.user.impl;

import com.datamindhub.blog.domain.user.Role;
import com.datamindhub.blog.domain.user.RoleEnum;
import com.datamindhub.blog.repository.user.RoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<Role> findByRoleEnum(RoleEnum roleEnum) {
        String jpql = "SELECT r FROM Role r WHERE r.name = :name";

        Role role = null;
        try {
             role = entityManager.createQuery(jpql, Role.class)
                    .setParameter("name", roleEnum)
                    .getSingleResult();
        } catch (NoResultException e) {
            log.info("역할 없음", e);
        }
        return Optional.ofNullable(role);
    }
}
