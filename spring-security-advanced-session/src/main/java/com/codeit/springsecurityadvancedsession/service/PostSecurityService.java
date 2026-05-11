package com.codeit.springsecurityadvancedsession.service;


import com.codeit.springsecurityadvancedsession.domain.Post;
import com.codeit.springsecurityadvancedsession.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service("postSecurityService")
public class PostSecurityService {

    private final PostRepository postRepository;

    public PostSecurityService(
            PostRepository postRepository
    ) {

        this.postRepository = postRepository;
    }

    /*
     * 현재 로그인 사용자와
     * 게시글 작성자를 비교
     */
    public boolean isAuthor(
            Long postId,
            String username
    ) {

        Post post =
                postRepository.findById(postId);

        if (post == null) {
            return false;
        }

        return username.equals(
                post.getAuthor()
        );
    }
}
