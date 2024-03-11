package com.datamindhub.blog.repository.user;

import com.datamindhub.blog.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByProviderId(String providerId);

    List<User> findAll();

    Long save(User user);
}
