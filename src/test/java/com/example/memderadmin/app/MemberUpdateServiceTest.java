package com.example.memderadmin.app;

import com.example.memderadmin.domain.FakeMemberRepository;
import com.example.memderadmin.exception.MemberNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class MemberUpdateServiceTest {

    private final MemberUpdateService memberUpdateService = new MemberUpdateService();

    @DisplayName("회원이 없으면 예외를 반환한다.")
    @Test
    void notFoundMember() {
        MemberUpdateRequest req = MemberUpdateRequest.of("변경제이슨",
                LocalDate.of(2023, 6, 4), "M", "qwer1234!@#", "");

        assertThatExceptionOfType(MemberNotFoundException.class)
                .isThrownBy( () -> memberUpdateService.update(req, 1L));
    }

}
