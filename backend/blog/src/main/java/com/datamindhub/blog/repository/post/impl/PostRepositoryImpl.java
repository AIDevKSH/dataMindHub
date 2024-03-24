package com.datamindhub.blog.repository.post.impl;

import com.datamindhub.blog.domain.post.Post;
import com.datamindhub.blog.domain.user.User;
import com.datamindhub.blog.repository.post.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {
    private final EntityManager entityManager;

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Post.class, id));
    }

    @Override
    public List<Post> findByTitle(String title) {
        String jpql = "SELECT p FROM Post p WHERE p.title = :title";
        return findAll(jpql, "title", title);
    }

    public <T> Optional<Post> findOne(String jpql, String col, T value) {
        Post post = null;

        try {
            post = entityManager.createQuery(jpql, Post.class)
                    .setParameter(col, value)
                    .getSingleResult();
        } catch (NoResultException e) {
            log.debug("게시글 조회 실패", e);
        }
        return Optional.ofNullable(post);
    }

    private <T> List<Post> findAll(String jpql, String col, T value) {
        List<Post> post = null;

        try {
            post = entityManager.createQuery(jpql, Post.class)
                    .setParameter(col, value)
                    .getResultList();
        } catch (RuntimeException e) {
            log.debug("게시글 조회 실패", e);
        }
        return post;
    }

    public List<Post> findAll() {
        String jpql = "SELECT p FROM Post p";
        return entityManager.createQuery(jpql, Post.class)
                .getResultList();
    }

    public Long save(Post post) {
        if (post.getId() != null) {
            entityManager.persist(post);
        } else {
            entityManager.merge(post);
        }
        return post.getId();
    }
}
