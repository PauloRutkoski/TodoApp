package com.rutkoski.todo.controllers;

import com.rutkoski.todo.exception.WsException;
import com.rutkoski.todo.service.AuthService;
import com.rutkoski.todo.to.CredentialsTO;
import com.rutkoski.todo.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping
    public ResponseEntity<CredentialsTO> authenticate(@RequestBody UserTO user) throws WsException {
        CredentialsTO credentials = service.authenticate(user);
        return ResponseEntity.ok(credentials);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody UserTO user) throws WsException {
        service.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<CredentialsTO> refresh(@RequestBody String refresh) throws WsException {
        CredentialsTO credentials = service.refreshAuthorization(refresh);
        return ResponseEntity.ok(credentials);
    }
}
