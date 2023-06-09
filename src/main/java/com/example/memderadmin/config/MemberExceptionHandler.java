package com.example.memderadmin.config;

import com.example.memderadmin.exception.AuthenticationMemberException;
import com.example.memderadmin.exception.InvalidPasswordException;
import com.example.memderadmin.exception.MemberNotFoundException;
import com.example.memderadmin.exception.NotAuthorizedMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler({InvalidPasswordException.class, MethodArgumentNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity<?> invalidRequest(Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> notFoundMember(MemberNotFoundException e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AuthenticationMemberException.class})
    public ResponseEntity<?> notMatchPassword(RuntimeException e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NotAuthorizedMemberException.class})
    public ResponseEntity<?> notAuthorized(NotAuthorizedMemberException e) {
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
}
