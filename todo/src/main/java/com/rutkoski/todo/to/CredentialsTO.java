package com.rutkoski.todo.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsTO implements Serializable {
    private String username;
    private String token;
    private String refreshToken;
}
