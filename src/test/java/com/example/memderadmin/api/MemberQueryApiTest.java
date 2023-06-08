package com.example.memderadmin.api;

import com.example.memderadmin.app.LoginRequest;
import com.example.memderadmin.app.LoginResponse;
import com.example.memderadmin.app.MemberRegisterRequest;
import com.example.memderadmin.app.MemberRegisterResponse;
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

class MemberQueryApiTest extends BaseController {

    @DisplayName("회원 조회 성공")
    @Test
    void success() {
        MemberRegisterRequest host = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        ExtractableResponse<Response> createResponse = MemberRegisterApiTest.callMemberRegisterApi(host);
        Long memberId = createResponse.body()
                .as(MemberRegisterResponse.class)
                .id();

        LoginRequest loginRequest = LoginRequest.of(host.loginId(), host.password());
        LoginResponse loginResponse = LoginApiTest.callLoginApi(loginRequest)
                .body()
                .as(LoginResponse.class);

        ExtractableResponse<Response> response = RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + loginResponse.token())
                .pathParam("memberId", memberId)
                .log().all()

                .when()
                .get("/intsvc/admin/homepage/v1/member/{memberId}")

                .then()
                .log().all()

                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("토큰이 없으면 예외를 반환한다.")
    @Test
    void emptyToken() {
        MemberRegisterRequest host = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        ExtractableResponse<Response> createResponse = MemberRegisterApiTest.callMemberRegisterApi(host);
        Long memberId = createResponse.body()
                .as(MemberRegisterResponse.class)
                .id();

        LoginRequest loginRequest = LoginRequest.of(host.loginId(), host.password());
        LoginResponse loginResponse = LoginApiTest.callLoginApi(loginRequest)
                .body()
                .as(LoginResponse.class);

        ExtractableResponse<Response> response = RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .pathParam("memberId", memberId)
                .log().all()

                .when()
                .get("/intsvc/admin/homepage/v1/member/{memberId}")

                .then()
                .log().all()

                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}