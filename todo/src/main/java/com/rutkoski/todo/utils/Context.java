package com.rutkoski.todo.utils;

import com.rutkoski.todo.model.User;
import lombok.Data;

import org.springframework.stereotype.Component;

@Component
@Data
public class Context {
    private User user;
}
