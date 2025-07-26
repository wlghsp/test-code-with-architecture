package com.example.demo.post.controller.response;

import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostResponseTest {

    @Test
    void Post로_응답을_생성할_수_있다() {
        // given
        Post post = Post.builder()
                .content("helloworld")
                .writer(User.builder()
                        .email("wlghsp@knou.ac.kr")
                        .nickname("aprilgone")
                        .address("Daejeon")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                        .build())
                .build();

        // when
        PostResponse postResponse = PostResponse.from(post);

        // then
        assertThat(postResponse.getContent()).isEqualTo("helloworld");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("wlghsp@knou.ac.kr");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("aprilgone");
        assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

}