package com.sneha.employee_management_backend.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
@Service
public class JwtService {

    // Minimum 32 characters
    private static final String SECRET_KEY =
            "mysecretkeymysecretkeymysecretkey12";

    private SecretKey getSignInKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
}

    public String generateToken(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignInKey())
                .compact();
    }
    public String extractUsername(String token) {

    Claims claims = Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();

    return claims.getSubject();
}
public boolean isTokenValid(String token, String username) {

    String extractedUsername = extractUsername(token);

    return extractedUsername.equals(username);
}
}