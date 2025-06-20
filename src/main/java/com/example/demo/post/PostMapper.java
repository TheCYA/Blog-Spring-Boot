package com.example.demo.post;

import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostResponse toPostResponse (Post post){
        return new PostResponse(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getAuthor().getUsername(),
            post.getCategory(),
            post.getCreatedAt(),
            post.getUpdatedAt()
        );
    }
}
