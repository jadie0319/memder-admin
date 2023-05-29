package com.example.memderadmin.app;

import com.example.memderadmin.common.PasswordValidator;
import com.example.memderadmin.domain.MemberRepository;
import com.example.memderadmin.domain.Member;
import com.example.memderadmin.exception.ExceptionMessages;
import com.example.memderadmin.exception.InvalidPasswordException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MemberRegistService {

    private final MemberRepository memberRepository;

    public MemberRegistService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberRegistResponse regist(MemberRegistRequest request) {
        validate(request);
        Member savedMember = memberRepository.save(Member.of(request));
        return MemberRegistResponse.builder()
                .id(savedMember.getId())
                .name(savedMember.getName())
                .role(savedMember.getRole())
                .loginId(savedMember.getLoginId())
                .regDt(LocalDateTime.now())
                .build();
    }

    private void validate(MemberRegistRequest request) {
        if (!PasswordValidator.validate(request.password())) {
            throw new InvalidPasswordException(ExceptionMessages.INVALID_PASSWORD);
        }

    }
}
