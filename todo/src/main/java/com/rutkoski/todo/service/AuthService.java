package com.rutkoski.todo.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.rutkoski.todo.domain.User;
import com.rutkoski.todo.enums.TokenTypeEnum;
import com.rutkoski.todo.exception.WsException;
import com.rutkoski.todo.to.CredentialsTO;
import com.rutkoski.todo.to.UserTO;
import com.rutkoski.todo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    private JwtUtils jwtUtils;

    public User register(UserTO userTO) throws WsException {
        assertUserNotExists(userTO.getUsername());
        User entity = new User(null, userTO.getUsername(), encryptPassword(userTO.getPassword()));
        return userService.persist(entity);
    }

    public CredentialsTO authenticate(UserTO userTO) throws WsException {
        validateCredentials(userTO);
        return usernameToCredentialsTO(userTO.getUsername());
    }

    private CredentialsTO usernameToCredentialsTO(String username) {
        String token = JwtUtils.generateToken(username, TokenTypeEnum.AUTHORIZATION);
        String refreshToken = JwtUtils.generateToken(username, TokenTypeEnum.REFRESH);
        return new CredentialsTO(username, token, refreshToken);
    }

    private void validateCredentials(UserTO userTO) throws WsException {
        User entity = userService.findByUsername(userTO.getUsername());
        if (entity == null) {
            throw new WsException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (!matchPasswords(userTO.getPassword(), entity.getPassword())) {
            throw new WsException(HttpStatus.BAD_REQUEST, "Invalid Credentials");
        }
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    private boolean matchPasswords(String memoryPassword, String dbPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(memoryPassword, dbPassword);
    }

    private void assertUserNotExists(String username) throws WsException {
        User entity = userService.findByUsername(username);
        if (entity != null) {
            throw new WsException(HttpStatus.BAD_REQUEST, "User already exists");
        }
    }

    private void assertUserExists(String username) throws WsException {
        User entity = userService.findByUsername(username);
        if (entity == null) {
            throw new WsException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    public CredentialsTO refreshAuthorization(String refreshToken) throws WsException {
        DecodedJWT decodedJWT = JwtUtils.getDecodedToken(refreshToken);
        String username = decodedJWT.getSubject();
        assertUserExists(username);
        return usernameToCredentialsTO(username);
    }
}
