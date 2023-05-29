package com.example.memderadmin.app;

import com.example.memderadmin.exception.InvalidPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class MemberRegistServiceTest {

    private MemberRegistService memberRegistService = new MemberRegistService();

    @DisplayName("패스워드 정책에 맞지 않으면 예외를 반환한다.")
    @Test
    void invalid() {
        MemberRegistRequest request = MemberRegistRequest.participant("제이디", LocalDate.of(2023, 4, 29), "M",
                "Jadie", "qwer1234", "홍어", "모임참여자 입니다.");

        assertThatExceptionOfType(InvalidPasswordException.class)
                .isThrownBy(() -> memberRegistService.regist(request));
    }

}
