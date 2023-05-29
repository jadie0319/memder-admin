package com.example.memderadmin.api;

import com.example.memderadmin.app.MemberRegistRequest;
import com.example.memderadmin.app.MemberRegistResponse;
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

class MemberRegistApiTest extends BaseController {

    @DisplayName("모임 주최자 등록")
    @Test
    void host() {
        MemberRegistRequest host = MemberRegistRequest.host("제이슨", LocalDate.of(2023, 5, 29),
                "M", "Dudu", "qwer1234!@#", "spring");

        ExtractableResponse<Response> response =
                RestAssured
                        .given()
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(ContentType.JSON)
                        .body(host)
                        .log().all()

                        .when()
                        .post("/extsvc/admin/homepage/v1/member")

                        .then()
                        .log().all()

                        .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        MemberRegistResponse result = response.body().as(MemberRegistResponse.class);
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("제이슨");
        assertThat(result.regDt()).isNotNull();
    }

    @DisplayName("모임 참여자 등록")
    @Test
    void participant() {
        MemberRegistRequest participant = MemberRegistRequest.participant("제이디", LocalDate.of(2023, 4, 29), "M",
                "Jadie", "qwer1234!@#", "홍어", "모임참여자 입니다.");

        ExtractableResponse<Response> response =
                RestAssured
                        .given()
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(ContentType.JSON)
                        .body(participant)
                        .log().all()

                        .when()
                        .post("/extsvc/admin/homepage/v1/member")

                        .then()
                        .log().all()

                        .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        MemberRegistResponse result = response.body().as(MemberRegistResponse.class);
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("제이디");
        assertThat(result.regDt()).isNotNull();
    }

    @DisplayName("모임 참여자 등록실패 - 비밀번호 정책 부합")
    @Test
    void invalidPassword() {
        MemberRegistRequest participant = MemberRegistRequest.participant("제이디", LocalDate.of(2023, 4, 29), "M",
                "Jadie", "qwer1234", "홍어", "모임참여자 입니다.");

        ExtractableResponse<Response> response =
                RestAssured
                        .given().log().all()
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(participant)

                        .when()
                        .post("/extsvc/admin/homepage/v1/member")

                        .then().log().all()

                        .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}
