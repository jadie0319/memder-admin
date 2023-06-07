package com.example.memderadmin.app;

import com.example.memderadmin.config.JwtTokenHandler;
import com.example.memderadmin.domain.FakeMemberRepository;
import com.example.memderadmin.domain.LoginMember;
import com.example.memderadmin.domain.Member;
import com.example.memderadmin.domain.MemberRepository;
import com.example.memderadmin.exception.AuthenticationMemberException;
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
