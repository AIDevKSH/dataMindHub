package com.datamindhub.blog.dto.post;

import com.datamindhub.blog.domain.post.Post;
import lombok.Data;

@Data
public class PostDto {
    private Long userId;
    private String title;
    private String body;
    private Byte status;

    public Post toEntity() {
        return Post.builder()
                .userId(this.userId)
                .title(this.title)
                .body(this.body)
                .status(1)
                .views(0)
                .build();
    }
}
