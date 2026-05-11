package com.codeit.springsecurityadvancedsession.service;

import com.codeit.springsecurityadvancedsession.domain.Post;
import com.codeit.springsecurityadvancedsession.repository.PostRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(
            PostRepository postRepository
    ) {

        this.postRepository = postRepository;
    }

    /*
     * 관리자이거나
     * 게시글 작성자인 경우만 접근 가능
     */
    @PreAuthorize(
            "hasRole('ADMIN') " +
                    "or " +
                    "@postSecurityService.isAuthor(#postId, authentication.name)"
    )
    public Post getPost(
            Long postId
    ) {

        return postRepository.findById(postId);
    }
}
