package org.wedding.common.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private static final SecretKey JWT_SECRET = Keys.hmacShaKeyFor(
        "odee's-wedding-planner-secret-key".getBytes(StandardCharsets.UTF_8)); // TODO: 환경변수로 변경

    public static String generateToken(int userId) {
        long now = System.currentTimeMillis();
        long JWT_EXPIRATION_MS = 864000000;     // 10일
        long expiry = now + JWT_EXPIRATION_MS;

        return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(expiry))
            .signWith(JWT_SECRET)
            .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int getUserIdFromToken(String token) {
        return Integer.parseInt(Jwts.parserBuilder()
            .setSigningKey(JWT_SECRET)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject());
    }
}
