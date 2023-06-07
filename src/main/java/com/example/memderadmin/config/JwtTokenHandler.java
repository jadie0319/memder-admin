package com.example.memderadmin.config;

import com.example.memderadmin.app.TokenHandler;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenHandler implements TokenHandler {

    private final String key;

    public JwtTokenHandler(@Value("${jwt-secreat-key}") String key) {
        this.key = key;
    }

    public String create(String loginId) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(Jwts.claims().setSubject(loginId))
                .setIssuer("member-admin")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + TimeUnit.HOURS.toMillis(1L)))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public boolean validate(String token, LocalDateTime now) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
    }

    public String getPayload(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }
}
