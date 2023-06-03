package com.example.memderadmin.config;

import com.example.memderadmin.app.TokenHandler;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Profile;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;


@Profile("test")
public class StubJwtTokenHandler implements TokenHandler {

    private String key = "testKey";

    @Override
    public String create(String loginId) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim("loginId", loginId)
                .setIssuer("member-admin")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofHours(2L).toMillis()))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(key.getBytes()))
                .compact();
    }
}
