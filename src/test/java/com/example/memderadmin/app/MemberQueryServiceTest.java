package com.example.memderadmin.app;

import com.example.memderadmin.domain.FakeMemberRepository;
import com.example.memderadmin.domain.LoginMember;
import com.example.memderadmin.domain.Member;
import com.example.memderadmin.exception.MemberNotFoundException;
import com.example.memderadmin.exception.NotAuthorizedMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class MemberQueryServiceTest {

    private final FakeMemberRepository memberRepository = new FakeMemberRepository();
    private final MemberQueryService memberQueryService = new MemberQueryService(memberRepository);

    @DisplayName("등록된 회원이 없으면 예외를 반환한다.")
    @Test
    void notFoundMember() {
        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> memberQueryService.getMember(1L, new LoginMember("Jadie")));
    }

    @DisplayName("로그인 아이디가 다르면 예외를 반환한다.")
    @Test
    void notMatchLoginId() {
        MemberRegisterRequest registerRequest = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        Member savedMember = memberRepository.save(Member.of(registerRequest));

        assertThatExceptionOfType(NotAuthorizedMemberException.class)
                .isThrownBy(() -> memberQueryService.getMember(1L, new LoginMember("Jadie")));
    }

}