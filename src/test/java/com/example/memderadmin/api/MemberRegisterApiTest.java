package com.example.memderadmin.api;

import com.example.memderadmin.app.MemberRegisterRequest;
import com.example.memderadmin.app.MemberRegisterResponse;
import com.example.memderadmin.domain.Role;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemberRegisterApiTest extends BaseController {

    @DisplayName("모임 주최자 등록")
    @Test
    void host() {
        MemberRegisterRequest host = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");

        ExtractableResponse<Response> response = callMemberRegisterApi(host);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        MemberRegisterResponse result = response.body().as(MemberRegisterResponse.class);
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("제이슨");
        assertThat(result.role()).isEqualTo(Role.HOST);
        assertThat(result.loginId()).isEqualTo("Dudu");
        assertThat(result.regDt()).isNotNull();
    }

    @DisplayName("모임 참여자 등록")
    @Test
    void participant() {
        MemberRegisterRequest participant = MemberRegisterRequest.participant("제이디", LocalDate.of(2023, 4, 29), "M",
                "Jadie", "qwer1234!@#", "jadie@gmail.com", "홍어", "모임참여자 입니다.");

        ExtractableResponse<Response> response = callMemberRegisterApi(participant);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        MemberRegisterResponse result = response.body().as(MemberRegisterResponse.class);
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("제이디");
        assertThat(result.role()).isEqualTo(Role.PARTICIPANT);
        assertThat(result.loginId()).isEqualTo("Jadie");
        assertThat(result.regDt()).isNotNull();
    }

    @DisplayName("모임 참여자 등록실패 - 비밀번호 정책 부합하지 않음")
    @Test
    void invalidPassword() {
        MemberRegisterRequest participant = MemberRegisterRequest.participant("제이디", LocalDate.of(2023, 4, 29), "M",
                "Jadie", "qwer1234", "jadie@gmail.com", "홍어", "모임참여자 입니다.");

        ExtractableResponse<Response> response = callMemberRegisterApi(participant);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("모임 참여자 등록실패 - 이메일 규칙 부합하지 않음")
    @Test
    void invalidEmail() {
        MemberRegisterRequest participant = MemberRegisterRequest.participant("제이디", LocalDate.of(2023, 4, 29), "M",
                "Jadie", "qwer1234!", "jadiegmail.com", "홍어", "모임참여자 입니다.");

        ExtractableResponse<Response> response = callMemberRegisterApi(participant);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private ExtractableResponse<Response> callMemberRegisterApi(MemberRegisterRequest req) {
        return RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .body(req)
                .log().all()

                .when()
                .post("/intsvc/admin/homepage/v1/member")

                .then()
                .log().all()

                .extract();
    }

}
