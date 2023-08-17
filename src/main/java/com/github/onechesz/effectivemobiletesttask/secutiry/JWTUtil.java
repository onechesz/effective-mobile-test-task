package com.github.onechesz.effectivemobiletesttask.secutiry;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(String name) {
        Date issuedAt = Date.from(ZonedDateTime.now().toInstant());
        Date expiresAt = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject("User data")
                .withClaim("name", name)
                .withIssuedAt(issuedAt)
                .withIssuer("Effective Mobile")
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User data")
                .withIssuer("Effective Mobile")
                .build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        return decodedJWT.getClaim("name").asString();
    }
}
