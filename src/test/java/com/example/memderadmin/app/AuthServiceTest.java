package com.example.memderadmin.app;

import com.example.memderadmin.config.JwtTokenHandler;
import com.example.memderadmin.domain.FakeMemberRepository;
import com.example.memderadmin.domain.LoginMember;
import com.example.memderadmin.domain.Member;
import com.example.memderadmin.domain.MemberRepository;
import com.example.memderadmin.exception.AuthenticationMemberException;
import com.example.memderadmin.exception.MemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class AuthServiceTest {

    private final MemberRepository memberRepository = new FakeMemberRepository();
    private final TokenHandler jwtTokenHandler = new JwtTokenHandler("testkey");
    private final AuthService authService = new AuthService(jwtTokenHandler, memberRepository);

    @DisplayName("회원이 등록되어 있지 않으면 예외를 반환한다.")
    @Test
    void notFoundMember() {
        LoginRequest req = LoginRequest.of("TomTom", "qwer1234!");

        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> authService.login(req));
    }

    @DisplayName("유효하지 않은 비밀번호를 입력하면 예외를 반환한다.")
    @Test
    void invalidPassword() {
        MemberRegisterRequest host = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        memberRepository.save(Member.of(host));
        LoginRequest req = LoginRequest.of("Dudu", "qwer1234!");

        assertThatExceptionOfType(AuthenticationMemberException.class)
                .isThrownBy(() -> authService.login(req));
    }

    @DisplayName("로그인 성공하면 토큰을 반환한다.")
    @Test
    void suceess() {
        MemberRegisterRequest host = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        memberRepository.save(Member.of(host));
        LoginRequest req = LoginRequest.of("Dudu", "qwer1234!@#");

        LoginResponse result = authService.login(req);

        assertThat(result.token()).isNotNull();
    }

    @DisplayName("인증시 유효기간이 끝난 토큰을 넣으면 예외를 반환한다.")
    @Test
    void fail() {
        String token = jwtTokenHandler.create("Jadie");

        assertThatExceptionOfType(AuthenticationMemberException.class)
                .isThrownBy(() -> authService.authentication(token, LocalDateTime.now().plusDays(2L)));
    }

    @DisplayName("인증 성공하면 LoginMember 를 반환한다.")
    @Test
    void success() {
        MemberRegisterRequest request = MemberRegisterRequest.participant("제이디", LocalDate.of(2023, 4, 29), "M",
                "Jadie", "qwer1234!", "jadie@gmail.com", "홍어", "모임참여자 입니다.");
        memberRepository.save(Member.of(request));

        String token = jwtTokenHandler.create("Jadie");
        LoginMember result = authService.authentication(token, LocalDateTime.now());

        assertThat(result.loginId()).isEqualTo(request.loginId());
    }

}
