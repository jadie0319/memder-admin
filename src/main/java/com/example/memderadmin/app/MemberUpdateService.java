package com.example.memderadmin.app;

import com.example.memderadmin.domain.Member;
import com.example.memderadmin.domain.MemberRepository;
import com.example.memderadmin.exception.ExceptionMessages;
import com.example.memderadmin.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MemberUpdateService {

    private final MemberRepository memberRepository;

    public MemberUpdateService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void update(MemberUpdateRequest request, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(ExceptionMessages.NOT_FOUND_MEMBER_ID.formatted(id)));


    }
}
