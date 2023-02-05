package com.rutkoski.todo.exception;

public class InvalidDataException  extends CustomException {
    public InvalidDataException(String message) {
        super(message);
    }
    
    public InvalidDataException(Exception e) {
    	super(e.getMessage());
    }
}
