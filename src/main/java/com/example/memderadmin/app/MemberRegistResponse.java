package com.example.memderadmin.app;

import java.time.LocalDateTime;

public record MemberRegistResponse(
        Long id,
        String name,
        LocalDateTime regDt
) {
}
