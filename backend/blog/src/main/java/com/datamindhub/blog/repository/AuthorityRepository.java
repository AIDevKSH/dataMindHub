package com.datamindhub.blog.repository;

import com.datamindhub.blog.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Optional<Authority> findByName(String name);
}
