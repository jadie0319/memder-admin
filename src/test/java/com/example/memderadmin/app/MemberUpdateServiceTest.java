package com.example.memderadmin.app;

import com.example.memderadmin.domain.FakeMemberRepository;
import com.example.memderadmin.domain.LoginMember;
import com.example.memderadmin.domain.Member;
import com.example.memderadmin.exception.MemberNotFoundException;
import com.example.memderadmin.exception.NotAuthorizedMemberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class MemberUpdateServiceTest {

    private final FakeMemberRepository memberRepository = new FakeMemberRepository();
    private final MemberUpdateService memberUpdateService = new MemberUpdateService(memberRepository);
    private MemberUpdateRequest memberUpdateRequest;

    @BeforeEach
    void setUp() {
        memberRepository.clear();
        memberUpdateRequest = MemberUpdateRequest.of("변경제이슨",
                LocalDate.of(2023, 6, 4), "M", "qwer1234!@#", "");
    }

    @DisplayName("회원이 없으면 예외를 반환한다.")
    @Test
    void notFoundMember() {
        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy(() -> memberUpdateService.update(memberUpdateRequest, 1L, null));
    }

    @DisplayName("로그인 아이디가 다르면 예외를 반환한다.")
    @Test
    void notMatchLoginId() {
        MemberRegisterRequest registerRequest = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        Member savedMember = memberRepository.save(Member.of(registerRequest));

        assertThatExceptionOfType(NotAuthorizedMemberException.class)
                .isThrownBy(() -> memberUpdateService.update(memberUpdateRequest, 1L, new LoginMember("Jaide")));
    }

    @DisplayName("회원변경성공")
    @Test
    void success() {
        MemberRegisterRequest registerRequest = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        Member savedMember = memberRepository.save(Member.of(registerRequest));

        memberUpdateService.update(memberUpdateRequest, savedMember.getId(), new LoginMember("Dudu"));

        Member updatedMember = memberRepository.findById(savedMember.getId())
                .get();
        assertThat(updatedMember.getName()).isEqualTo(memberUpdateRequest.name());
        assertThat(updatedMember.getBirthDate()).isEqualTo(memberUpdateRequest.birthDate());
    }

}
