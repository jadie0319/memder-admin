package com.example.memderadmin.api;

import com.example.memderadmin.app.MemberRegistRequest;
import com.example.memderadmin.app.MemberRegistResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class MemberRegistApi {

    @PostMapping("/extsvc/admin/homepage/v1/member")
    public ResponseEntity<?> regist(MemberRegistRequest request) {
        return ResponseEntity.ok(new MemberRegistResponse(1L,"Name", LocalDateTime.now()));
    }
}
