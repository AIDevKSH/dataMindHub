package com.datamindhub.blog.repository;

import com.datamindhub.blog.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Byte> {
    Authority findByName(String name);
}
