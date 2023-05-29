package com.example.memderadmin.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PasswordValidatorTest {


    @DisplayName("영문자, 숫자, 특수문자가 하나이상 포함된 8자리 문자열이 아닌 경우 false")
    @ParameterizedTest
    @ValueSource(strings = {"12345", "12345678", "abc12345", "abc!@#$%", "123!@#$%"})
    void valid(String input) {
        boolean result = PasswordValidator.validate(input);
        assertThat(result).isFalse();
    }

    @DisplayName("영문자, 숫자, 특수문자가 하나이상 포함된 8자리 문자열인 경우 true")
    @ParameterizedTest
    @ValueSource(strings = {"abc1234!", "abc!@#$5", "123!@#$a"})
    void invalid(String input) {
        boolean result = PasswordValidator.validate(input);
        assertThat(result).isTrue();
    }

}
