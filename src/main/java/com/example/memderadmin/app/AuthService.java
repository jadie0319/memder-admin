package com.example.memderadmin.app;

import com.example.memderadmin.domain.LoginMember;
import com.example.memderadmin.domain.MemberRepository;
import com.example.memderadmin.exception.AuthenticationMemberException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.memderadmin.exception.ExceptionMessages.FAIL_AUTHENTICATION;

@Service
public class AuthService {

    private final TokenHandler tokenHandler;
    private final MemberRepository memberRepository;

    public AuthService(TokenHandler tokenHandler, MemberRepository memberRepository) {
        this.tokenHandler = tokenHandler;
        this.memberRepository = memberRepository;
    }

    public LoginMember authentication(String token, LocalDateTime now) {
        if (!tokenHandler.validate(token, now)) {
            throw new AuthenticationMemberException(FAIL_AUTHENTICATION);
        }
        String loginId = tokenHandler.getPayload(token);
        return new LoginMember(loginId);
    }
}
