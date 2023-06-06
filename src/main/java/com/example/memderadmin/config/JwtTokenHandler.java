package com.example.memderadmin.config;

import com.example.memderadmin.app.TokenHandler;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenHandler implements TokenHandler {

    private final String key;

    public JwtTokenHandler(@Value("${jwt-secreat-key}") String key) {
        this.key = key;
    }

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

    public boolean validate(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }
}
