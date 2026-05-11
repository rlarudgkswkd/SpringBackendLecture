package com.codeit.springsecurityadvancedsession.repository;

import com.codeit.springsecurityadvancedsession.domain.Post;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PostRepository {

    private final Map<Long, Post> posts =
            new HashMap<>();

    @PostConstruct
    public void init() {

        posts.put(
                1L,
                new Post(
                        1L,
                        "USER 게시글",
                        "user 작성 글",
                        "user"
                )
        );

        posts.put(
                2L,
                new Post(
                        2L,
                        "ADMIN 게시글",
                        "admin 작성 글",
                        "admin"
                )
        );
    }

    public Post findById(Long id) {

        return posts.get(id);
    }
}
