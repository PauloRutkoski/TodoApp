package com.rutkoski.todo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WsException extends Exception{
    private HttpStatus status;

    public WsException(HttpStatus status, String message){
        super(message);
        this.status= status;
    }
}
