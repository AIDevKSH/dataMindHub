package com.datamindhub.blog.repository.user.impl;

import com.datamindhub.blog.domain.user.Authority;
import com.datamindhub.blog.domain.user.AuthorityEnum;
import com.datamindhub.blog.domain.user.Role;
import com.datamindhub.blog.repository.user.AuthorityRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AuthorityRepositoryImpl implements AuthorityRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<Authority> findByAuthorityEnum(AuthorityEnum authorityEnum) {
        String jpql = "SELECT a FROM Authority a WHERE a.name = :name";

        Authority authority = null;
        try {
            authority = entityManager.createQuery(jpql, Authority.class)
                    .setParameter("name", authorityEnum)
                    .getSingleResult();
        } catch (NoResultException e) {
            log.info("권한 없음", e);
        }
        return Optional.ofNullable(authority);
    }
}
