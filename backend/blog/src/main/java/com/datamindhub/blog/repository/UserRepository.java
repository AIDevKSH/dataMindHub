package com.datamindhub.blog.repository;

import com.datamindhub.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface UserRepository {
    Optional<User> findByEmail(String email);

    Optional<User> findByProviderId(String providerId);

    List<User> findAll();

    void save(User user);
}
