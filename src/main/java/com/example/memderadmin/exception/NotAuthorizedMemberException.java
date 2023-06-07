package com.example.memderadmin.exception;

public class NotAuthorizedMemberException extends RuntimeException {
    private final String message;

    public NotAuthorizedMemberException(String message) {
        this.message = message;
    }
}
