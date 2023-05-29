package com.example.memderadmin.app;

import lombok.Builder;

import java.time.LocalDateTime;

public record MemberRegistResponse(
        Long id,
        String name,
        LocalDateTime regDt
) {

    @Builder
    public MemberRegistResponse {}
}
