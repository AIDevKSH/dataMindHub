package com.datamindhub.blog.service.post;

import com.datamindhub.blog.dto.post.PostDto;
import com.datamindhub.blog.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void save(PostDto postDto) {
        postRepository.save(postDto.toEntity());
    }

    public void view() {

    }

    public void update() {

    }

    public void delete() {

    }
}
