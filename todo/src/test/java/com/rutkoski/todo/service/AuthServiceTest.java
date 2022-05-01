package com.rutkoski.todo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rutkoski.todo.enums.TokenTypeEnum;
import com.rutkoski.todo.exception.InvalidDataException;
import com.rutkoski.todo.exception.NotFoundException;
import com.rutkoski.todo.model.User;
import com.rutkoski.todo.test.utils.MockTestBean;
import com.rutkoski.todo.to.CredentialsTO;
import com.rutkoski.todo.to.UserTO;
import com.rutkoski.todo.utils.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthServiceTest extends MockTestBean {
    @Autowired
    private AuthService authService;
    @MockBean
    private UserService userService;
    private static MockedStatic<JwtUtils> jwtUtils;

    @BeforeAll
    static void beforeAll() {
        jwtUtils = Mockito.mockStatic(JwtUtils.class, Mockito.CALLS_REAL_METHODS);
    }

    @BeforeEach
    void beforeEach() {
        jwtUtils.reset();
    }

    @Test
    void registerWhenUserAlreadyExists() {
        Mockito.when(userService.findByUsername(ArgumentMatchers.any())).thenReturn(new User());

        InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            authService.register(new UserTO());
        });
    }

    @Test
    void registerUserSuccess() throws Exception {
        UserTO userTO = new UserTO("user", "123456");
        User registeredUser = new User(1L, "user", "123456");

        Mockito.when(userService.findByUsername(ArgumentMatchers.any())).thenReturn(null);
        Mockito.when(userService.persist(ArgumentMatchers.any())).thenReturn(registeredUser);

        User user = authService.register(userTO);
        assertEquals(registeredUser, user);
    }

    @Test
    void authenticateWithNotFoundUser() {
        Mockito.when(userService.findByUsername(ArgumentMatchers.any())).thenReturn(null);

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            authService.authenticate(new UserTO());
        });
    }

    @Test
    void authenticateWithWrongPassword() {
        UserTO userTO = new UserTO("user", "123456");
        User dbUser = new User(1L, "user", "1234567");

        Mockito.when(userService.findByUsername(ArgumentMatchers.any())).thenReturn(dbUser);
        InvalidDataException exception = Assertions.assertThrows(InvalidDataException.class, () -> {
            authService.authenticate(userTO);
        });
    }

    @Test
    void authenticateSuccess() throws Exception {
        final String authorization = "1";
        final String refresh = "2";
        jwtUtils.when(() -> JwtUtils.generateToken(ArgumentMatchers.any(), ArgumentMatchers.eq(TokenTypeEnum.AUTHORIZATION))).thenReturn(authorization);
        jwtUtils.when(() -> JwtUtils.generateToken(ArgumentMatchers.any(), ArgumentMatchers.eq(TokenTypeEnum.REFRESH))).thenReturn(refresh);

        UserTO userTO = new UserTO("user", "123456");
        User dbUser = new User(1L, "user", new BCryptPasswordEncoder().encode("123456"));

        Mockito.when(userService.findByUsername(ArgumentMatchers.any())).thenReturn(dbUser);
        CredentialsTO credentialsTO = authService.authenticate(userTO);

        assertEquals(userTO.getUsername(), credentialsTO.getUsername());
        assertEquals(authorization, credentialsTO.getToken());
        assertEquals(refresh, credentialsTO.getRefreshToken());
    }

    @Test
    void refreshAuthorizationEmptyToken() {
        JWTVerificationException exception = Assertions.assertThrows(JWTVerificationException.class, () -> {
            authService.refreshAuthorization("");
        });
    }

    @Test
    void refreshAuthorizationInvalidSignatureToken() {
        String invalidToken = JWT.create().withSubject("user")
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .sign(Algorithm.HMAC512("InvalidSecret"));
        JWTVerificationException exception = Assertions.assertThrows(JWTVerificationException.class, () -> {
            authService.refreshAuthorization(invalidToken);
        });
    }

    @Test
    void refreshAuthorizationNotFoundUser() {
        String token = JwtUtils.generateToken("user", TokenTypeEnum.REFRESH);
        Mockito.when(userService.findByUsername(ArgumentMatchers.any())).thenReturn(null);

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class, () -> {
            authService.refreshAuthorization(token);
        });
    }

    @Test
    void refreshAuthorizationSuccess() throws Exception {
        final String authorization = "1";
        final String refresh = "2";
        final String username = "user";
        String token = JwtUtils.generateToken(username, TokenTypeEnum.REFRESH);

        jwtUtils.when(() -> JwtUtils.generateToken(ArgumentMatchers.any(), ArgumentMatchers.eq(TokenTypeEnum.AUTHORIZATION))).thenReturn(authorization);
        jwtUtils.when(() -> JwtUtils.generateToken(ArgumentMatchers.any(), ArgumentMatchers.eq(TokenTypeEnum.REFRESH))).thenReturn(refresh);
        Mockito.when(userService.findByUsername(ArgumentMatchers.any())).thenReturn(new User());

        CredentialsTO credentialsTO = authService.refreshAuthorization(token);

        assertEquals(username, credentialsTO.getUsername());
        assertEquals(authorization, credentialsTO.getToken());
        assertEquals(refresh, credentialsTO.getRefreshToken());
    }

}