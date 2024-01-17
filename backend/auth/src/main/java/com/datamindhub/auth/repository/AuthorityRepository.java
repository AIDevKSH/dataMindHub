package com.datamindhub.auth.repository;

import com.datamindhub.auth.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Optional<Authority> findByName(String name);
}
