package com.rutkoski.todo.exception;

public abstract class CustomException extends Exception{
    public CustomException(String message) {
        super(message);
    }
}
