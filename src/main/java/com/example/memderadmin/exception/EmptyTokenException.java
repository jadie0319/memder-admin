package com.example.memderadmin.exception;

public class EmptyTokenException extends RuntimeException {
    public EmptyTokenException(String message) {
        super(message);
    }
}
