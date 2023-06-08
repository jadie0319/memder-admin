package com.example.memderadmin.api;

import com.example.memderadmin.app.*;
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

class MemberRoleChangeApiTest extends BaseController {

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
        MemberRegisterResponse memberResponse = createResponse.body().as(MemberRegisterResponse.class);

        MemberRoleChangeRequest changeRequest = MemberRoleChangeRequest.toHost("스프링방");

        ExtractableResponse<Response> response = RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + loginResponse.token())

                .body(changeRequest)
                .pathParam("memberId", memberResponse.id())
                .log().all()

                .when()
                .put("/intsvc/admin/homepage/v1/member/{memberId}/role")

                .then()
                .log().all()

                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

}
