package com.sam.taskManagement.utils;

import com.sam.taskManagement.domain.userAuthAndRegistration.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtUtil {

    private static final SecretKey SECRET_KEY;

    static {
        try {
            SECRET_KEY = KeyGenerator.getInstance("HmacSHA256").generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(String username, List<Role> roles) {
        log.info("Request received to generate token.");

        // Ensure roles are properly formatted
        List<String> roleClaims = roles.stream()
                .map(role -> "ROLE_" + role.name().toUpperCase()) // Convert Role enum to uppercase string with prefix
                .toList();


        return Jwts.builder()
                .claim("roles", roleClaims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract Claims from Token
    public static Claims extractClaims(String token) {
        log.info("Request received to extract claims.");
        Claims claims = Jwts.parserBuilder()

                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        log.info("Claims:{} ", claims);
        return claims;
    }

    public static String extractUsername(String token) {
        log.info("Request received to extract username.");
        return extractClaims(token).getSubject();
    }

    // Validate Token
    public static boolean isTokenExpired(String token) {
        log.info("Request received to validate token expiration.");
        return extractClaims(token).getExpiration().before(new Date());
    }

    public static boolean validateToken(String token, String username) {
        log.info("Request received to validate token.");
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

//    public static Claims extractClaimsFromRequest() {
//        String token = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
//                .getRequest().getHeader("Authorization").replace("Bearer ", "");
//        return extractClaims(token);
//    }
}

