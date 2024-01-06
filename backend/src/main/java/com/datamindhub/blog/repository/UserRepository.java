package com.datamindhub.blog.repository;

import com.datamindhub.blog.domain.Authority;
import com.datamindhub.blog.domain.User;
import com.datamindhub.blog.domain.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
