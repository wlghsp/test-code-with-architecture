package com.example.demo.user.controller;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.TestContainer;
import com.example.demo.user.controller.response.MyProfileResponse;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class UserControllerTest {

    @Test
    void 사용자는_특정_유저의_정보를_개인정보는_소거된채_전달_받을_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                        .id(1L)
                        .email("wlghsp@knou.ac.kr")
                        .nickname("aprilgone")
                        .address("Daejeon")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                        .lastLoginAt(100L)
                        .build()
        );

        // when
        ResponseEntity<UserResponse> result = testContainer.userController.getById(1L);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("wlghsp@knou.ac.kr");
        assertThat(result.getBody().getNickname()).isEqualTo("aprilgone");
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(100L);
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_존재하지_않는_유저의_아디로_api_호출할_경우_404_응답을_받는다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        // when && then
        assertThatThrownBy(() -> testContainer.userController.getById(1L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("wlghsp@knou.ac.kr")
                .nickname("aprilgone")
                .address("Daejeon")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .lastLoginAt(100L)
                .build()
        );

        // when
        ResponseEntity<Void> result = testContainer.userController.verifyEmail(1L, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(testContainer.userRepository.getById(1L).getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("wlghsp@knou.ac.kr")
                .nickname("aprilgone")
                .address("Daejeon")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .lastLoginAt(100L)
                .build()
        );

        // when && then
        assertThatThrownBy(() -> testContainer.userController.verifyEmail(1L, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
            .isInstanceOf(CertificationCodeNotMatchedException.class);
    }

    @Test
    void 사용자는_내_정보를_블러올_때_개인정보인_주소도_갖고_올_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .initialMillis(1678530673958L)
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("wlghsp@knou.ac.kr")
                .nickname("aprilgone")
                .address("Daejeon")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .lastLoginAt(100L)
                .build()
        );

        // when
        ResponseEntity<MyProfileResponse> result = testContainer.userController.getMyInfo("wlghsp@knou.ac.kr");

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("wlghsp@knou.ac.kr");
        assertThat(result.getBody().getNickname()).isEqualTo("aprilgone");
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(1678530673958L);
        assertThat(result.getBody().getAddress()).isEqualTo("Daejeon");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }


    @Test
    void 사용자는_내_정보를_수정할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("wlghsp@knou.ac.kr")
                .nickname("aprilgone")
                .address("Daejeon")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .lastLoginAt(100L)
                .build()
        );

        // when
        ResponseEntity<MyProfileResponse> result = testContainer.userController.updateMyInfo(
                "wlghsp@knou.ac.kr", UserUpdate.builder()
                                .address("Jeonju")
                                .nickname("maygone")
                        .build());

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("wlghsp@knou.ac.kr");
        assertThat(result.getBody().getNickname()).isEqualTo("maygone");
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(100L);
        assertThat(result.getBody().getAddress()).isEqualTo("Jeonju");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }



}

