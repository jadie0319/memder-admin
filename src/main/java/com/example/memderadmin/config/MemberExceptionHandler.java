package com.example.memderadmin.config;

import com.example.memderadmin.exception.InvalidPasswordException;
import com.example.memderadmin.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler({InvalidPasswordException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<?> passwordException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> notFoundMember(MemberNotFoundException e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
