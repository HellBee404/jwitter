package com.hellbee.jwitter.security.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {

    @Value("${app.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${app.jwt.expiration-time}")
    private long EXPIRATION_TIME;

    public String generateToken(UserDetails userDetails) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        log.debug("Generating JWT token for user: {}, expires at: {}",
                userDetails.getUsername(), expirationDate);

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        log.debug("Generating JWT token for user: {}, expires at: {}",
                userDetails.getUsername(), expirationDate);

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String extractUsername(String token) {
        log.debug("Extracting username from token: {}", token);

        DecodedJWT decodedJWT = JWT.decode(token);

//        if(decodedJWT == null) {
//            log.warn("Failed to extract username from token: {}", token);
//            return null;
//        }

        return decodedJWT.getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        log.debug("Validating token for user: {}", userDetails.getUsername());
        final String username = extractUsername(token);

        if (username == null) {
            log.warn("Token validation failed: could not extract username");
            return false;
        }


        if (!username.equals(userDetails.getUsername())) {
            log.warn("Token username mismatch. Token: {}, expected: {}",
                    username, userDetails.getUsername());
            return false;
        }

        if (isTokenExpired(token)) {
            log.warn("Token expired: {}", token);
            return false;
        }

        log.debug("Token validation successful for user: {}", username);
        return true;
    }

    private boolean isTokenExpired(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);

//        if (decodedJWT == null) {
//            return true;
//        }

        Date expiration = decodedJWT.getExpiresAt();
        boolean expired = expiration.before(new Date());

        if (expired) {
            log.debug("Token expired at: {}", expiration);
        }

        return expired;
    }

    private DecodedJWT verifyToken(String token) {
        log.trace("Attempting to decode token: {}", token);

        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token);

            log.trace("Token decoded successfully: {}", decodedJWT.getSubject());

            return decodedJWT;
        } catch (JWTVerificationException e) {
            log.error("Token verification failed for token: {}. Reason: {}",
                    token, e.getMessage());
            return null;
        }
    }
}
