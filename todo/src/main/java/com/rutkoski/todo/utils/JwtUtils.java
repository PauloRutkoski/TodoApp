package com.rutkoski.todo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rutkoski.todo.enums.TokenTypeEnum;

import java.util.Date;

public class JwtUtils {
    private final static String PREFIX = "Bearer";
    private final static Long JWT_TOKEN_VALIDITY = 1L;
    private final static Long JWT_REFRESH_VALIDITY = 168L;
    private final static String SECRET = "94c6167c-2b81-4fa2-9c2a-679d118f9fb9";

    public static String generateToken(String subject, TokenTypeEnum type) {
        return JWT.create().withSubject(subject).withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(getExpiresAtDefault(type))
                .sign(getAlgorithm());
    }

    public static DecodedJWT getDecodedToken(String token) throws JWTVerificationException {
        if (token == null || token.trim().isEmpty()) {
            throw new JWTVerificationException("Not valid token structure");
        }
        token = token.replaceAll(PREFIX, "").trim();
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        return verifier.verify(token);
    }

    private static Algorithm getAlgorithm() {
        return Algorithm.HMAC512(SECRET);
    }

    private static Date getExpiresAtDefault(TokenTypeEnum type) {
        if (type == TokenTypeEnum.AUTHORIZATION) {
            return expireDateFromHours(JWT_TOKEN_VALIDITY);
        }
        return expireDateFromHours(JWT_REFRESH_VALIDITY);
    }

    private static Date expireDateFromHours(Long hours) {
        return new Date(System.currentTimeMillis() + hours * 60 * 60 * 1000);
    }

    private static Boolean isTokenExpired(DecodedJWT decodedJWT, TokenTypeEnum type) {
        final Date expiration = decodedJWT.getExpiresAt();
        if (type == TokenTypeEnum.AUTHORIZATION) {
            return expiration.before(getExpiresAtDefault(type));
        }
        return false;
    }
}
