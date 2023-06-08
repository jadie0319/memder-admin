package com.example.memderadmin.app;

import com.example.memderadmin.config.JwtTokenHandler;
import com.example.memderadmin.domain.FakeMemberRepository;
import com.example.memderadmin.domain.Member;
import com.example.memderadmin.exception.AuthenticationMemberException;
import com.example.memderadmin.exception.MemberNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class LoginServiceTest {


    private final FakeMemberRepository memberRepository = new FakeMemberRepository();
    private final TokenHandler jwtTokenHandler = new JwtTokenHandler("testkey");
    private final LoginService loginService = new LoginService(memberRepository, jwtTokenHandler);

    @BeforeEach
    void setUp() {
        memberRepository.clear();
    }

    @DisplayName("회원이 등록되어 있지 않으면 예외를 반환한다.")
    @Test
    void notFoundMember() {
        LoginRequest req = LoginRequest.of("TomTom", "qwer1234!");

        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> loginService.login(req));
    }

    @DisplayName("유효하지 않은 비밀번호를 입력하면 예외를 반환한다.")
    @Test
    void invalidPassword() {
        MemberRegisterRequest host = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        memberRepository.save(Member.of(host));
        LoginRequest req = LoginRequest.of("Dudu", "qwer1234!");

        assertThatExceptionOfType(AuthenticationMemberException.class)
                .isThrownBy(() -> loginService.login(req));
    }

    @DisplayName("로그인 성공하면 토큰을 반환한다.")
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
