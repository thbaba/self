package com.denizcanbagdatlioglu.self.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.denizcanbagdatlioglu.self.common.ApplicationConstants;
import com.denizcanbagdatlioglu.self.user.domain.entity.User;

import java.nio.charset.StandardCharsets;

@Component
public class JwtUtil {

    private final SecretKey SIGNING_KEY = Keys.hmacShaKeyFor(ApplicationConstants.JWT_SECRET_DEFAULT_VALUE.getBytes(StandardCharsets.UTF_8));

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.id().getValue())
                .signWith(SIGNING_KEY)
                .compact();
    }

    // public boolean isTokenValid(String token, String username) {
    //     Claims claims = extractClaims(token);
    //     return isSubjectValid(claims, username) && isExpirationDateValid(claims);
    // }

    // public String getUsername(String token) {
    //     return extractClaims(token).getSubject();
    // }

    // private boolean isSubjectValid(Claims claims, String expectedSubject) {
    //     return claims.getSubject().equals(expectedSubject);
    // }

    // private Claims extractClaims(String token) {
    //     return Jwts.parser().verifyWith(SIGNING_KEY).build().parseSignedClaims(token).getPayload();
    // }

}