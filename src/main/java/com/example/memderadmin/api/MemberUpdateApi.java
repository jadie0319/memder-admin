package com.example.memderadmin.api;

import com.example.memderadmin.app.MemberUpdateRequest;
import com.example.memderadmin.app.MemberUpdateService;
import com.example.memderadmin.config.MemberToken;
import com.example.memderadmin.domain.LoginMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MemberUpdateApi {

    private final MemberUpdateService memberUpdateService;

    public MemberUpdateApi(MemberUpdateService memberUpdateService) {
        this.memberUpdateService = memberUpdateService;
    }

    @PutMapping("/intsvc/admin/homepage/v1/member/{memberId}")
    public ResponseEntity<?> update(
            @RequestBody @Valid MemberUpdateRequest request,
            @PathVariable("memberId") Long id,
            @RequestBody LoginMember loginMember
    ) {
        memberUpdateService.update(request,id);
        return ResponseEntity.noContent().build();
    }
}
