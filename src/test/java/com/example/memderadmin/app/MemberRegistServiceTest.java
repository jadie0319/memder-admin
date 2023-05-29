package com.example.memderadmin.app;

import com.example.memderadmin.domain.FakeMemberRepository;
import com.example.memderadmin.domain.MemberRepository;
import com.example.memderadmin.domain.Role;
import com.example.memderadmin.exception.InvalidPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

class MemberRegistServiceTest {

    private final MemberRepository memberRepository = new FakeMemberRepository();
    private final MemberRegistService memberRegistService = new MemberRegistService(memberRepository);

    @DisplayName("패스워드 정책에 맞지 않으면 예외를 반환한다.")
    @Test
    void invalid() {
        MemberRegistRequest request = MemberRegistRequest.participant("제이디", LocalDate.of(2023, 4, 29), "M",
                "Jadie", "qwer1234", "jadie@gmail.com", "홍어", "모임참여자 입니다.");

        assertThatExceptionOfType(InvalidPasswordException.class)
                .isThrownBy(() -> memberRegistService.regist(request));
    }

    @DisplayName("모임 참여자 등록")
    @Test
    void successParticipant() {
        MemberRegistRequest request = MemberRegistRequest.participant("제이디", LocalDate.of(2023, 4, 29), "M",
                "Jadie", "qwer1234!", "jadie@gmail.com", "홍어", "모임참여자 입니다.");

        MemberRegistResponse response = memberRegistService.regist(request);

        assertThat(response.name()).isEqualTo(request.name());
        assertThat(response.role()).isEqualTo(Role.PARTICIPANT);
        assertThat(response.loginId()).isEqualTo(request.loginId());
    }

    @DisplayName("모임 주최자 등록")
    @Test
    void successHost() {
        MemberRegistRequest request = MemberRegistRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#","dudu@gmail.com", "spring");

        MemberRegistResponse response = memberRegistService.regist(request);

        assertThat(response.name()).isEqualTo(request.name());
        assertThat(response.role()).isEqualTo(Role.HOST);
        assertThat(response.loginId()).isEqualTo(request.loginId());
    }

}
