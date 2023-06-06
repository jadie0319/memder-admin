package com.example.memderadmin.app;

import com.example.memderadmin.domain.LoginMember;
import com.example.memderadmin.exception.AuthenticationMemberException;
import com.example.memderadmin.exception.ExceptionMessages;
import org.springframework.stereotype.Service;

import static com.example.memderadmin.exception.ExceptionMessages.FAIL_AUTHENTICATION;

@Service
public class AuthService {

    private final TokenHandler tokenHandler;

    public AuthService(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    public LoginMember authentication(String token) {
        if (!tokenHandler.validate(token)) {
            throw new AuthenticationMemberException(FAIL_AUTHENTICATION);
        }

        return null;
    }
}
