package com.example.memderadmin.app;

import com.example.memderadmin.domain.FakeMemberRepository;
import com.example.memderadmin.domain.Member;
import com.example.memderadmin.exception.MemberNotFoundException;
import com.example.memderadmin.exception.NotMatchPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class LoginServiceTest {


    private final FakeMemberRepository memberRepository = new FakeMemberRepository();
    private final LoginService loginService = new LoginService(memberRepository);

    @BeforeEach
    void setUp() {
        memberRepository.clear();
    }

    @Test
    void notFoundMember() {
        LoginRequest req = LoginRequest.of("TomTom", "qwer1234!");

        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> loginService.login(req));
    }

    @Test
    void invalidPassword() {
        MemberRegisterRequest host = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        memberRepository.save(Member.of(host));
        LoginRequest req = LoginRequest.of("Dudu", "qwer1234!");

        assertThatExceptionOfType(NotMatchPasswordException.class)
                .isThrownBy(() -> loginService.login(req));
    }

    @Test
    void suceess() {
        MemberRegisterRequest host = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        memberRepository.save(Member.of(host));
        LoginRequest req = LoginRequest.of("Dudu", "qwer1234!@#");

        LoginResponse result = loginService.login(req);

        assertThat(result.token()).isNotNull();
    }

}
