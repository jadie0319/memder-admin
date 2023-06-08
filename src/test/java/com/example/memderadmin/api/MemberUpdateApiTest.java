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

public class MemberUpdateApiTest extends BaseController {

    @Test
    void success() {
        MemberRegisterRequest host = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        ExtractableResponse<Response> createResponse = MemberRegisterApiTest.callMemberRegisterApi(host);
        LoginRequest loginRequest = LoginRequest.of(host.loginId(), host.password());
        LoginResponse loginResponse = LoginApiTest.callLoginApi(loginRequest)
                .body()
                .as(LoginResponse.class);


        MemberRegisterResponse memberResponse = createResponse.body().as(MemberRegisterResponse.class);
        MemberUpdateRequest req = MemberUpdateRequest.of("변경제이슨",
                LocalDate.of(2023, 6, 4), "M", "qwer1234!@#", "dudu@gmail.com");

        ExtractableResponse<Response> response = RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + loginResponse.token())

                .body(req)
                .pathParam("memberId", memberResponse.id())
                .log().all()

                .when()
                .put("/intsvc/admin/homepage/v1/member/{memberId}")

                .then()
                .log().all()

                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("토큰이 없으면 예외를 반환한다.")
    @Test
    void emptyToken() {
        MemberRegisterRequest host = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        ExtractableResponse<Response> createResponse = MemberRegisterApiTest.callMemberRegisterApi(host);
        LoginRequest loginRequest = LoginRequest.of(host.loginId(), host.password());
        LoginResponse loginResponse = LoginApiTest.callLoginApi(loginRequest)
                .body()
                .as(LoginResponse.class);


        MemberRegisterResponse memberResponse = createResponse.body().as(MemberRegisterResponse.class);
        MemberUpdateRequest req = MemberUpdateRequest.of("변경제이슨",
                LocalDate.of(2023, 6, 4), "M", "qwer1234!@#", "dudu@gmail.com");

        ExtractableResponse<Response> response = RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)

                .body(req)
                .pathParam("memberId", memberResponse.id())
                .log().all()

                .when()
                .put("/intsvc/admin/homepage/v1/member/{memberId}")

                .then()
                .log().all()

                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }
}
