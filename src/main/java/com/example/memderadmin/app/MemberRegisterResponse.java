package com.example.memderadmin.app;

import com.example.memderadmin.domain.Role;
import lombok.Builder;

import java.time.LocalDateTime;

public record MemberRegisterResponse(
        Long id,
        String name,
        Role role,
        String loginId,
        LocalDateTime regDt
) {

    @Builder
    public MemberRegisterResponse {}
}
