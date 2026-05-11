package com.codeit.springsecurityadvancedsession.controller;

import com.codeit.springsecurityadvancedsession.domain.Post;
import com.codeit.springsecurityadvancedsession.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(
            PostService postService
    ) {

        this.postService = postService;
    }

    @GetMapping("/posts/{postId}")
    public Post getPost(
            @PathVariable Long postId
    ) {

        return postService.getPost(postId);
    }
}
