package com.rutkoski.todo.controllers;

import com.rutkoski.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/hello", produces = "application/json")
public class HelloController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<String> hello(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(Date.valueOf(LocalDate.now()));
        StringBuilder builder = new StringBuilder();
        builder.append("Hello ").append(SecurityContextHolder.getContext().getAuthentication().getName());
        builder.append(" today is ").append(date);
        return ResponseEntity.ok(builder.toString());
    }
}
