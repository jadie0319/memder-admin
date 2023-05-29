package com.example.memderadmin.api;

import com.example.memderadmin.app.MemberRegistRequest;
import com.example.memderadmin.app.MemberRegistResponse;
import com.example.memderadmin.app.MemberRegistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class MemberRegistApi {

    private final MemberRegistService memberRegistService;

    public MemberRegistApi(MemberRegistService memberRegistService) {
        this.memberRegistService = memberRegistService;
    }

    @PostMapping("/extsvc/admin/homepage/v1/member")
    public ResponseEntity<?> regist(MemberRegistRequest request) {
        return ResponseEntity.ok(memberRegistService.regist(request));
    }
}
