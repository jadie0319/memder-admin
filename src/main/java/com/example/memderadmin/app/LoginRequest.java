package com.example.memderadmin.app;

public record LoginRequest(
        String loginId,
        String password
) {
    public static LoginRequest of(String loginId, String password) {
        return new LoginRequest(loginId, password);
    }
}
