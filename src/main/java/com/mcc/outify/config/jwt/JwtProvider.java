package com.mcc.outify.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String secrtKey;

    public String create(String account) {
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS)); // 만료 시간 설정: 1시간
        Key key = Keys.hmacShaKeyFor(secrtKey.getBytes(StandardCharsets.UTF_8)); // Secret key 생성

        String jwt = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(account)
                .setIssuedAt(new Date()).setExpiration(expiredDate)
                .compact();

        return jwt;
    }

    public String validate(String jwt) {
        String subject = null;

        Key key = Keys.hmacShaKeyFor(secrtKey.getBytes(StandardCharsets.UTF_8));

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            subject = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return subject;
    }

}
