package com.example.memderadmin.app;

import com.example.memderadmin.domain.LoginMember;
import com.example.memderadmin.domain.Member;
import com.example.memderadmin.domain.MemberRepository;
import com.example.memderadmin.exception.ExceptionMessages;
import com.example.memderadmin.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public MemberQueryService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberQueryResponse getMember(Long id, LoginMember loginMember) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(ExceptionMessages.NOT_FOUND_MEMBER_ID.formatted(id)));
        member.isMatchLoginMember(loginMember.loginId());

        return null;
    }
}
