package com.rutkoski.todo.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.rutkoski.todo.enums.TokenTypeEnum;
import com.rutkoski.todo.exception.CustomException;
import com.rutkoski.todo.exception.InvalidDataException;
import com.rutkoski.todo.exception.NotFoundException;
import com.rutkoski.todo.model.User;
import com.rutkoski.todo.to.CredentialsTO;
import com.rutkoski.todo.to.UserTO;
import com.rutkoski.todo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    private JwtUtils jwtUtils;

    public User register(UserTO userTO) throws CustomException {
        assertUserNotExists(userTO.getUsername());
        User entity = new User(null, userTO.getUsername(), encryptPassword(userTO.getPassword()));
        return userService.persist(entity);
    }

    public CredentialsTO authenticate(UserTO userTO) throws CustomException {
        validateCredentials(userTO);
        return usernameToCredentialsTO(userTO.getUsername());
    }

    private CredentialsTO usernameToCredentialsTO(String username) {
        String token = JwtUtils.generateToken(username, TokenTypeEnum.AUTHORIZATION);
        String refreshToken = JwtUtils.generateToken(username, TokenTypeEnum.REFRESH);
        return new CredentialsTO(username, token, refreshToken);
    }

    private void validateCredentials(UserTO userTO) throws CustomException {
        User entity = userService.findByUsername(userTO.getUsername());
        if (entity == null) {
            throw new NotFoundException("User not found");
        }
        if (!matchPasswords(userTO.getPassword(), entity.getPassword())) {
            throw new InvalidDataException("Invalid Credentials");
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

    private void assertUserNotExists(String username) throws InvalidDataException {
        User entity = userService.findByUsername(username);
        if (entity != null) {
            throw new InvalidDataException("User already exists");
        }
    }

    private void assertUserExists(String username) throws CustomException {
        User entity = userService.findByUsername(username);
        if (entity == null) {
            throw new NotFoundException("User not found");
        }
    }

    public CredentialsTO refreshAuthorization(String refreshToken) throws CustomException {
        DecodedJWT decodedJWT = JwtUtils.getDecodedToken(refreshToken);
        String username = decodedJWT.getSubject();
        assertUserExists(username);
        return usernameToCredentialsTO(username);
    }
}
