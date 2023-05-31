package com.example.memderadmin.app;

public record LoginResponse(
        String token
) {
    public static LoginResponse of(String token) {
        return new LoginResponse(token);
    }
}
