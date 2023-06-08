package com.example.memderadmin.api;

import com.example.memderadmin.app.MemberQueryService;
import com.example.memderadmin.config.AuthenticationMember;
import com.example.memderadmin.domain.LoginMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberQueryApi {

    private final MemberQueryService memberQueryService;

    public MemberQueryApi(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    @GetMapping("/intsvc/admin/homepage/v1/member/{memberId}")
    public ResponseEntity<?> getMember(@PathVariable("memberId") Long memberId, @AuthenticationMember LoginMember loginMember) {
        return ResponseEntity.ok(memberQueryService.getMember(memberId, loginMember));
    }

}
