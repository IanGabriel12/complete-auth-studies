package com.iangabrieldev.spring_boot_auth.jwt;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time-seconds}")
    private Long jwtExpirationInSeconds;

    private String issuer = "authapi";
    public String generateToken(UserDetails user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
            .withIssuer(issuer)
            .withIssuedAt(creationDate())
            .withExpiresAt(expiresAt())
            .withSubject(user.getUsername())
            .sign(algorithm);
    }

    public String getSubjectFromToken(String token) {
        return getDecodedJWT(token)
            .getSubject();
    }

    public Long getExpiresAtFromToken(String token) {
        return getDecodedJWT(token)
            .getExpiresAt().getTime();
    }

    private DecodedJWT getDecodedJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.require(algorithm)
            .withIssuer(issuer)
            .build()
            .verify(token);
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).toInstant();
    }

    private Instant expiresAt() {
        return ZonedDateTime.now(ZoneId.of("America/Recife")).plusSeconds(jwtExpirationInSeconds).toInstant();
    }
}
