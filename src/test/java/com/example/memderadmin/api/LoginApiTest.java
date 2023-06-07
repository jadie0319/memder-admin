package com.example.memderadmin.api;

import com.example.memderadmin.app.LoginRequest;
import com.example.memderadmin.app.LoginResponse;
import com.example.memderadmin.app.MemberRegisterRequest;
import com.example.memderadmin.config.JwtTokenHandler;
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

public class LoginApiTest extends BaseController {

    @Autowired
    private JwtTokenHandler jwtTokenHandler;

    @DisplayName("등록된 회원이 없으면 예외를 반환한다.")
    @Test
    void notFoundMember() {
        LoginRequest req = LoginRequest.of("jadie", "qwer1234!");
        ExtractableResponse<Response> response = callLoginApi(req);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @DisplayName("로그인 성공시 인증토큰을 반환한다.")
    @Test
    void loginSuccess() {
        MemberRegisterRequest host = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        MemberRegisterApiTest.callMemberRegisterApi(host);
        LoginRequest req = LoginRequest.of(host.loginId(), host.password());

        ExtractableResponse<Response> response = callLoginApi(req);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        LoginResponse result = response.body().as(LoginResponse.class);
        assertThat(result.token()).isNotNull();
    }

    @DisplayName("패스워드 틀렸을 때 예외를 반환한다.")
    @Test
    void invalidPassword() {
        MemberRegisterRequest host = MemberRegisterRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "dudu@gmail.com", "spring");
        MemberRegisterApiTest.callMemberRegisterApi(host);
        LoginRequest req = LoginRequest.of(host.loginId(), "1234abcde!");

        ExtractableResponse<Response> response = callLoginApi(req);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    public static ExtractableResponse<Response> callLoginApi(LoginRequest req) {
        ExtractableResponse<Response> response = RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(ContentType.JSON)

                .body(req)
                .log().all()

                .when()
                .post("/intsvc/admin/homepage/v1/login")

                .then()
                .log().all()

                .extract();
        return response;
    }

}
