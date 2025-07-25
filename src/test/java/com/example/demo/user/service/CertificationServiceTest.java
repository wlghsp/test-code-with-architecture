package com.example.demo.user.service;

import com.example.demo.mock.FakeMailSender;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CertificationServiceTest {

    @Test
    void 이메일과_컨텐츠가_제대로_만들어져서_보내지는지_테스트한다() {
        // given
        FakeMailSender fakeMailSender = new FakeMailSender();
        CertificationService certificationService = new CertificationService(fakeMailSender);

        // when
        int userId = 1;
        String certificationCode = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab";
        certificationService.send("wlghsp@knou.ac.kr", userId, certificationCode);

        // then
        assertThat(fakeMailSender.email).isEqualTo("wlghsp@knou.ac.kr");
        assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
        assertThat(fakeMailSender.content).isEqualTo("Please click the following link to certify your email address: " + "http://localhost:8080/api/users/" + userId + "/verify?certificationCode=" + certificationCode);
    }
}