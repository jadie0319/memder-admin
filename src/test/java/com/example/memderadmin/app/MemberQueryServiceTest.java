package com.example.memderadmin.app;

import com.example.memderadmin.domain.FakeMemberRepository;
import com.example.memderadmin.domain.LoginMember;
import com.example.memderadmin.exception.MemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class MemberQueryServiceTest {

    private final FakeMemberRepository memberRepository = new FakeMemberRepository();
    private final MemberQueryService memberQueryService = new MemberQueryService(memberRepository);

    @DisplayName("등록된 회원이 없으면 예외를 반환한다.")
    @Test
    void notFound() {
        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> memberQueryService.getMember(1L, new LoginMember("Jadie")));
    }

}