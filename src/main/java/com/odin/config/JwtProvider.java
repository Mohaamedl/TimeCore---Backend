package com.odin.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Provider class for JWT token generation and validation.
 * Handles token creation, parsing, and validation operations.
 */
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private static SecretKey key;
    
    static {
        try {
            String secretKey = JwtConstant.SECRETE_KEY;
            if (secretKey == null || secretKey.trim().isEmpty()) {
                throw new IllegalStateException("JWT secret key not found. Set JWT_SECRET_KEY environment variable.");
            }
            key = Keys.hmacShaKeyFor(secretKey.getBytes());
        } catch (Exception e) {
            logger.error("Failed to initialize JwtProvider: {}", e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Generates a JWT token for an authenticated user
     * @param auth Authentication object containing user details
     * @return Generated JWT token string
     */
    public static String generateToken(Authentication auth)
    {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthrities(authorities);

        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 86400000))
                .claim("email",auth.getName())
                .claim("authorities",roles)
                .signWith(key)
                .compact();

        return jwt;
    }

    /**
     * Extracts email from JWT token
     * @param token JWT token to parse
     * @return Email address from token claims
     */
    public static String getEmailFromToken(String token)
    {
        token = token.substring(7);

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String email = String.valueOf(claims.get("email"));

        return email;
    }

    private static String populateAuthrities(Collection<?  extends GrantedAuthority> authorities)
    {
        Set<String> auth = new HashSet<>();
        for (GrantedAuthority ga : authorities)
        {
            auth.add(ga.getAuthority());
        }
        return String.join(",",auth);

    }
}
