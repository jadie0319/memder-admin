package com.example.memderadmin.api;

import com.example.memderadmin.app.MemberRegisterRequest;
import com.example.memderadmin.app.MemberRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MemberRegisterApi {

    private final MemberRegisterService memberRegisterService;

    public MemberRegisterApi(MemberRegisterService memberRegisterService) {
        this.memberRegisterService = memberRegisterService;
    }

    @PostMapping("/intsvc/admin/homepage/v1/member")
    public ResponseEntity<?> regist(@RequestBody @Valid MemberRegisterRequest request) {
        return new ResponseEntity<>(memberRegisterService.regist(request), HttpStatus.CREATED);
    }
}
