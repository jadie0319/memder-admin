package com.example.memderadmin.app;

import com.example.memderadmin.domain.*;
import com.example.memderadmin.exception.ExceptionMessages;
import com.example.memderadmin.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import static com.example.memderadmin.exception.ExceptionMessages.*;

@Service
public class MemberRoleUpdateService {

    private final MemberRepository memberRepository;

    public MemberRoleUpdateService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void updateRole(MemberRoleUpdateRequest request, Long id, LoginMember loginMember) {
        validate(request);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(ExceptionMessages.NOT_FOUND_MEMBER_ID.formatted(id)));
        member.isMatchLoginMember(loginMember.loginId());

        member.updateRole(MemberRoleUpdateDto.of(request));
    }

    private void validate(MemberRoleUpdateRequest request) {
        if (ObjectUtils.isEmpty(request.role())) throw new IllegalArgumentException(EMPTY_ROLE);
        if (Role.HOST.equals(request.role())) {
            if (ObjectUtils.isEmpty(request.group())) throw new IllegalArgumentException(EMPTY_GROUP);
        }

        if (Role.PARTICIPANT.equals(request.role())) {
            if (ObjectUtils.isEmpty(request.limitIngredients())) throw new IllegalArgumentException(EMPTY_LIMIT_INGREDIENTS);
            if (ObjectUtils.isEmpty(request.description())) throw new IllegalArgumentException(EMPTY_DESCRIPTION);
            if (!ObjectUtils.isEmpty(request.group())) throw new IllegalArgumentException(NOT_EMPTY_GROUP);
        }
    }


}
