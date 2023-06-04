package com.example.memderadmin.app;

import com.example.memderadmin.domain.Member;
import com.example.memderadmin.domain.MemberRepository;
import com.example.memderadmin.exception.ExceptionMessages;
import com.example.memderadmin.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final TokenHandler tokenHandler;

    public LoginService(MemberRepository memberRepository, TokenHandler tokenHandler) {
        this.memberRepository = memberRepository;
        this.tokenHandler = tokenHandler;
    }

    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByLoginId(request.loginId())
                .orElseThrow(() -> new MemberNotFoundException(ExceptionMessages.NOT_FOUND_MEMBER_LOGIN_ID.formatted(request.loginId())));
        member.checkPassword(request.password());

        String token = tokenHandler.create(request.loginId());

        return LoginResponse.of(token);
    }
}
