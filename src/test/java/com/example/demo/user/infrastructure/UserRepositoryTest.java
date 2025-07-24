package com.example.demo.user.infrastructure;

import com.example.demo.user.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource("classpath:test-application.properties")
@Sql("/sql/user-repository-test-data.sql")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByIdAndStatus_로_유저_데이터를_찾아올_수_있다() {
        Optional<UserEntity> result = userRepository.findByIdAndStatus(1L, UserStatus.ACTIVE);

        assertThat(result.isPresent()).isTrue();
    }


    @Test
    void findByIdAndStatus_데이터가_없으면_Optional_empty_를_내려준다() {
        Optional<UserEntity> result = userRepository.findByIdAndStatus(1L, UserStatus.PENDING);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void findByEmailAndStatus_로_유저_데이터를_찾아올_수_있다() {
        Optional<UserEntity> result = userRepository.findByEmailAndStatus("wlghsp@knou.ac.kr", UserStatus.ACTIVE);

        assertThat(result.isPresent()).isTrue();
    }


    @Test
    void findByEmailAndStatus_데이터가_없으면_Optional_empty_를_내려준다() {
        Optional<UserEntity> result = userRepository.findByEmailAndStatus("wlghsp@knou.ac.kr", UserStatus.PENDING);

        assertThat(result.isEmpty()).isTrue();
    }
}