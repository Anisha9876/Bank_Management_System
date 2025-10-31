package com.example.Bank_Anisha.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key for signing token (keep it safe)
    private static final String SECRET = "MySuperSecretKeyForJWTToken12345";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 1. Generate JWT token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)                   // put username in payload
                .setIssuedAt(new Date())                // current time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(key, SignatureAlgorithm.HS256) // sign with secret key
                .compact();
    }

    // 2. Validate token
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true; // token is valid
        } catch (JwtException e) {
            return false; // token invalid or expired
        }
    }

    // 3. Extract username from token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}