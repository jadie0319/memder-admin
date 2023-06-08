package com.example.memderadmin.exception;

public class NotAuthorizedMemberException extends RuntimeException {

    public NotAuthorizedMemberException(String message) {
        super(message);
    }
}
