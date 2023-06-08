package com.example.memderadmin.api;

import com.example.memderadmin.app.MemberRoleChangeRequest;
import com.example.memderadmin.config.AuthenticationMember;
import com.example.memderadmin.domain.LoginMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MemberRoleChangeApi {

    @PutMapping("/intsvc/admin/homepage/v1/member/{memberId}/role")
    public ResponseEntity<?> update(
            @RequestBody @Valid MemberRoleChangeRequest memberRoleChangerequest,
            @PathVariable("memberId") Long id,
            @AuthenticationMember LoginMember loginMember
    ) {

        return ResponseEntity.noContent().build();
    }
}
