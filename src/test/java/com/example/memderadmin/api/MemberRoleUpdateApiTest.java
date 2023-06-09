package com.example.memderadmin.api;

import com.example.memderadmin.app.*;
import com.example.memderadmin.domain.Member;
import com.example.memderadmin.domain.MemberRepository;
import com.example.memderadmin.domain.Role;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemberRoleUpdateApiTest extends BaseController {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("소속을 입력받아 참여자를 주최자로 변경")
    @Test
    void toHost() {
        MemberRegisterRequest participant = MemberRegisterRequest.participant("제이디", LocalDate.of(2023, 4, 29), "M",
                "Jadie", "qwer1234!@#", "jadie@gmail.com", "홍어", "모임참여자 입니다.");
        ExtractableResponse<Response> createResponse = MemberRegisterApiTest.callMemberRegisterApi(participant);
        LoginRequest loginRequest = LoginRequest.of(participant.loginId(), participant.password());
        LoginResponse loginResponse = LoginApiTest.callLoginApi(loginRequest)
                .body()
                .as(LoginResponse.class);
        Long memberId = createResponse.body()
                .as(MemberRegisterResponse.class)
                .id();

        MemberRoleUpdateRequest changeRequest = MemberRoleUpdateRequest.toHost("스프링방");

        ExtractableResponse<Response> response = RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + loginResponse.token())

                .body(changeRequest)
                .pathParam("memberId", memberId)
                .log().all()

                .when()
                .put("/intsvc/admin/homepage/v1/member/{memberId}/role")

                .then()
                .log().all()

                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        Member savedMember = memberRepository.findById(memberId).get();
        assertThat(savedMember.getRole()).isEqualTo(Role.HOST);
    }

    @DisplayName("주최자를 참여자로 변경")
    @Test
    void toParticipant() {
        MemberRegisterRequest host = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        ExtractableResponse<Response> createResponse = MemberRegisterApiTest.callMemberRegisterApi(host);
        LoginRequest loginRequest = LoginRequest.of(host.loginId(), host.password());
        LoginResponse loginResponse = LoginApiTest.callLoginApi(loginRequest)
                .body()
                .as(LoginResponse.class);
        Long memberId = createResponse.body()
                .as(MemberRegisterResponse.class)
                .id();

        MemberRoleUpdateRequest changeRequest = MemberRoleUpdateRequest.toParticipant("취식제한재료","자기소개");

        ExtractableResponse<Response> response = RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + loginResponse.token())

                .body(changeRequest)
                .pathParam("memberId", memberId)
                .log().all()

                .when()
                .put("/intsvc/admin/homepage/v1/member/{memberId}/role")

                .then()
                .log().all()

                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        Member savedMember = memberRepository.findById(memberId).get();
        assertThat(savedMember.getRole()).isEqualTo(Role.PARTICIPANT);
    }

}
