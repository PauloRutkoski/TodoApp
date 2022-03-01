package com.rutkoski.todo.to;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CredentialsTO implements Serializable {
    private String username;
    private String token;
    private String refreshToken;
}
