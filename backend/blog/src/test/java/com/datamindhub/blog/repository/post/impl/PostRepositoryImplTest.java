package com.datamindhub.blog.repository.post.impl;

import com.datamindhub.blog.domain.post.Post;
import com.datamindhub.blog.domain.user.User;
import com.datamindhub.blog.repository.post.PostRepository;
import com.datamindhub.blog.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class PostRepositoryImplTest {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("id로 게시글 조회")
    public void findById() {
        // given
        User user = User.builder().email("test").build();
        Long userId = userRepository.save(user);  // 회원 저장

        Post post = Post.builder().userId(userId).title("첫번째글").body("안녕하세요").build();
        Long savedId = postRepository.save(post);  // 게시글 저장

        // when
        Post foundPost = postRepository.findById(post.getId()).orElseThrow();

        // then
        Assertions.assertThat(foundPost.getId()).isEqualTo(savedId);
    }
}