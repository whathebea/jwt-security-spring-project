package com.whathebea.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "452948404D635166546A576E5A7234753778214125442A472D4A614E64526755";
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private byte[] getSecretKey() {
        byte[] decodedKey = java.util.Base64.getDecoder().decode(SECRET_KEY);
        return decodedKey;
    }
}
