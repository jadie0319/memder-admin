package com.example.memderadmin.app;

import java.time.LocalDateTime;

public interface TokenHandler {
    String create(String loginId);
    boolean validate(String token, LocalDateTime now);

    String getPayload(String token);
}
