package com.denizcanbagdatlioglu.self.config.jwt;

import com.denizcanbagdatlioglu.self.user.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {

    private final SecretKey SIGNING_KEY;

    public JwtUtil(@Value("${self.jwt-secret}") String jwtSecret) {
        SIGNING_KEY = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.id().getValue())
                .signWith(SIGNING_KEY)
                .compact();
    }

    public String getID(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().verifyWith(SIGNING_KEY).build().parseSignedClaims(token).getPayload();
    }

}