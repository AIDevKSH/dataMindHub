package com.datamindhub.blog.repository.post;

import com.datamindhub.blog.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(Long id);
    List<Post> findByTitle(String title);

    Long save(Post post);

    List<Post> findAll();
}
