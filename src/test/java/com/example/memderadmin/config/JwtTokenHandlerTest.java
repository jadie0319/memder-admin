package com.example.memderadmin.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JwtTokenHandlerTest {

    private final String testKey = "testKey";
    private final JwtTokenHandler tokenHandler = new JwtTokenHandler(testKey);

    @DisplayName("토큰 생성")
    @Test
    void success() {
        String token = tokenHandler.create("jadie");
        assertThat(tokenHandler.validate(token, LocalDateTime.now())).isTrue();
    }

    @DisplayName("유효기간이 지나면 false 를 반환한다.")
    @Test
    void failed() {
        String token = tokenHandler.create("jadie");

        assertThat(tokenHandler.validate(token, LocalDateTime.now().plusDays(2L))).isFalse();
    }

}
