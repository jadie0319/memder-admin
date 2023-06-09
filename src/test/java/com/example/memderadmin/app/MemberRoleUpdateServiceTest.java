package com.example.memderadmin.app;

import com.example.memderadmin.domain.FakeMemberRepository;
import com.example.memderadmin.domain.LoginMember;
import com.example.memderadmin.domain.Member;
import com.example.memderadmin.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MemberRoleUpdateServiceTest {

    private final FakeMemberRepository memberRepository = new FakeMemberRepository();
    private final MemberRoleUpdateService memberRoleUpdateService = new MemberRoleUpdateService(memberRepository);
    private Member savedPartipant;
    private Member savedHost;

    @BeforeEach
    void setUp() {
        memberRepository.clear();
        MemberRegisterRequest registerParticipantRequest = MemberRegisterRequest.participant("제이디", LocalDate.of(2023, 4, 29), "M",
                "Jadie", "qwer1234!", "jadie@gmail.com", "홍어", "모임참여자 입니다.");
        savedPartipant = memberRepository.save(Member.of(registerParticipantRequest));

        MemberRegisterRequest registerHostRequest = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        savedHost = memberRepository.save(Member.of(registerHostRequest));
    }

    @DisplayName("역할 변경시 역할이 null이면 예외 발생")
    @Test
    void validateToHostNotFoundRole() {
        MemberRoleUpdateRequest req = MemberRoleUpdateRequest.builder().build();

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> memberRoleUpdateService.updateRole(req, savedPartipant.getId(), null));
    }

    @DisplayName("주최자로 변경할 때 소속이 없으면 예외 발생")
    @Test
    void validateToHostNotFoundGroup() {
        MemberRoleUpdateRequest req = MemberRoleUpdateRequest.toHost(null);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> memberRoleUpdateService.updateRole(req, savedPartipant.getId(), null));
    }

    @DisplayName("참여자로 변경할 때 취식제한재료가 없으면 예외 발생")
    @Test
    void validateToParticipantNotFoundLimitIngredients() {
        MemberRoleUpdateRequest req = MemberRoleUpdateRequest.toParticipant(null, "자기소개");

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> memberRoleUpdateService.updateRole(req, savedPartipant.getId(), null));
    }

    @DisplayName("참여자로 변경할 때 자기소개가 없으면 예외 발생")
    @Test
    void validateToParticipantNotFoundDescription() {
        MemberRoleUpdateRequest req = MemberRoleUpdateRequest.toParticipant("취식제한재료", null);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> memberRoleUpdateService.updateRole(req, savedPartipant.getId(), null));
    }

    @DisplayName("참여자로 변경")
    @Test
    void successToParticipant() {
        MemberRoleUpdateRequest req = MemberRoleUpdateRequest.toParticipant("취식제한재료", "자기소개");

        memberRoleUpdateService.updateRole(req, savedHost.getId(), new LoginMember("Dudu"));

        Member savedMember = memberRepository.findByLoginId("Dudu").get();
        assertThat(savedMember.getRole()).isEqualTo(Role.PARTICIPANT);
    }

    @DisplayName("주최자로 변경")
    @Test
    void successToHost() {
        MemberRoleUpdateRequest req = MemberRoleUpdateRequest.toHost("스프링방입니다.");

        memberRoleUpdateService.updateRole(req, savedPartipant.getId(), new LoginMember("Jadie"));

        Member savedMember = memberRepository.findByLoginId("Jadie").get();
        assertThat(savedMember.getRole()).isEqualTo(Role.HOST);
    }

}