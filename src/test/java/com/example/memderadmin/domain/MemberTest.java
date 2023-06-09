package com.example.memderadmin.domain;

import com.example.memderadmin.app.MemberRegisterRequest;
import com.example.memderadmin.app.MemberUpdateRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    void createHost() {
        Member member = Member.of(
                MemberRegisterRequest.host("제이슨"
                        , LocalDate.of(2023, 5, 29)
                        , "M"
                        , "Dudu"
                        , "qwer1234!@#"
                        , "dudu@gmail.com"
                        , "spring")
        );
        assertThat(member.getRole()).isEqualTo(Role.HOST);
    }

    @Test
    void createParticipant() {
        Member member = Member.of(
                MemberRegisterRequest.participant("제이디"
                        , LocalDate.of(2023, 4, 29)
                        , "M"
                        ,"Jadie"
                        , "qwer1234!"
                        , "jadie@gmail.com"
                        , "홍어"
                        , "모임참여자 입니다.")
        );
        assertThat(member.getRole()).isEqualTo(Role.PARTICIPANT);
    }

    @Test
    void update() {
        Member member = Member.of(
                MemberRegisterRequest.participant("제이디"
                        , LocalDate.of(2023, 4, 29)
                        , "M"
                        ,"Jadie"
                        , "qwer1234!"
                        , "jadie@gmail.com"
                        , "홍어"
                        , "모임참여자 입니다.")
        );
        member.update(
                MemberUpdateDto.builder()
                        .name("NewJadie")
                        .birthDate(LocalDate.of(2023,1,1))
                        .build());

        assertThat(member.getName()).isEqualTo("NewJadie");
        assertThat(member.getBirthDate()).isEqualTo(LocalDate.of(2023,1,1));
        assertThat(member.getGender()).isEqualTo(Gender.MALE);
        assertThat(member.getEmail()).isEqualTo("jadie@gmail.com");
    }

    @Test
    void updateRole() {
        Member member = Member.of(
                MemberRegisterRequest.participant("제이디"
                        , LocalDate.of(2023, 4, 29)
                        , "M"
                        ,"Jadie"
                        , "qwer1234!"
                        , "jadie@gmail.com"
                        , "홍어"
                        , "모임참여자 입니다.")
        );
        member.updateRole(
                MemberRoleUpdateDto.builder()
                        .role(Role.HOST)
                        .group("소속")
                        .limitIngredients("홍어")
                        .build()
        );

        assertThat(member.getRole()).isEqualTo(Role.HOST);
        assertThat(member.getGroup()).isEqualTo("소속");
        assertThat(member.getLimitIngredients()).isEqualTo("홍어");
        assertThat(member.getGender()).isEqualTo(Gender.MALE);
        assertThat(member.getEmail()).isEqualTo("jadie@gmail.com");
    }

}