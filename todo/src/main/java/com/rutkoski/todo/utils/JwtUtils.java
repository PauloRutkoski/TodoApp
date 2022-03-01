package com.rutkoski.todo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rutkoski.todo.enums.TokenTypeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {
    @Value("${jwt.exp_token}")
    private Long JWT_TOKEN_VALIDITY;
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String subject, TokenTypeEnum type) {
        return JWT.create().withSubject(subject).withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(getExpiresAtDefault(type))
                .sign(getAlgorithm());
    }

    public DecodedJWT getDecodedToken(String token) throws JWTVerificationException {
        if (token == null || token.isEmpty()) {
            throw new JWTVerificationException("Not valid token structure");
        }
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        return verifier.verify(token);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC512(secret);
    }

    private Date getExpiresAtDefault(TokenTypeEnum type) {
        if (type == TokenTypeEnum.AUTHORIZATION) {
           return new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY);
        }
        return null;
    }

    private Boolean isTokenExpired(DecodedJWT decodedJWT, TokenTypeEnum type) {
        final Date expiration = decodedJWT.getExpiresAt();
        if (type == TokenTypeEnum.AUTHORIZATION) {
            return expiration.before(getExpiresAtDefault(type));
        }
        return false;
    }
}
