package com.Substring.auth.Security;

import com.Substring.auth.entities.Role;
import com.Substring.auth.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {

    private final SecretKey key;
    private final long accessTtlSeconds;
    private final long refreshTtlSeconds;
    private final String issuer;

    // ================= CONSTRUCTOR =================
    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.access-ttl-seconds}") long accessTtlSeconds,
            @Value("${security.jwt.refresh-ttl-seconds}") long refreshTtlSeconds,
            @Value("${security.jwt.issuer}") String issuer
    ) {

        // ---- validation ----
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("JWT secret missing");
        }

        if (secret.length() < 64) {
            throw new IllegalArgumentException(
                    "JWT secret must be at least 64 characters for HS512");
        }

        // convert secret → cryptographic key
        byte[] keyBytes = secret.getBytes();
        this.key = Keys.hmacShaKeyFor(keyBytes);

        this.accessTtlSeconds = accessTtlSeconds;
        this.refreshTtlSeconds = refreshTtlSeconds;
        this.issuer = issuer;
    }

    // ================= ACCESS TOKEN =================
    public String generateAccessToken(User user) {

        Instant now = Instant.now();

        List<String> roles = user.getRoles() == null
                ? List.of()
                : user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(user.getId().toString())
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(accessTtlSeconds)))
                .addClaims(Map.of(
                        "email", user.getEmail(),
                        "roles", roles,
                        "typ", "access"
                ))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // ================= REFRESH TOKEN =================
    public String generateRefreshToken(User user, String jti) {

        Instant now = Instant.now();

        return Jwts.builder()
                .setId(jti)
                .setSubject(user.getId().toString())
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(refreshTtlSeconds)))
                .claim("typ", "refresh")
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // ================= PARSE TOKEN =================
    public Jws<Claims> parse(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }

    // ================= TOKEN TYPE CHECK =================
    public boolean isAccessToken(String token) {
        Claims claims = parse(token).getBody();
        return "access".equals(claims.get("typ"));
    }

    public boolean isRefreshToken(String token) {
        Claims claims = parse(token).getBody();
        return "refresh".equals(claims.get("typ"));
    }

    // ================= EXTRACT DATA =================
    public UUID getUserId(String token) {
        Claims claims = parse(token).getBody();
        return UUID.fromString(claims.getSubject());
    }

    public String getJti(String token) {
        return parse(token).getBody().getId();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token) {
        Claims claims = parse(token).getBody();
        return (List<String>) claims.get("roles");
    }

    // ================= VALIDATION =================
    public boolean isTokenValid(String token) {
        try {
            parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}