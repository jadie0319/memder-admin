package com.example.memderadmin.domain;

import com.example.memderadmin.app.MemberUpdateRequest;
import lombok.Builder;

import java.time.LocalDate;

public record MemberUpdateDto(
        String name,
        LocalDate birthDate,
        String gender,
        String password,
        String email
) {
    @Builder
    public MemberUpdateDto {}

    public static MemberUpdateDto of(MemberUpdateRequest request) {
        return new MemberUpdateDto(
                request.name(), request.birthDate(), request.gender(), request.password(), request.email()
        );
    }
}
