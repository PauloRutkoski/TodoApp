package com.rutkoski.todo.exception;

import com.rutkoski.todo.utils.GlobalLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> wsException(NotFoundException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<String> wsException(InvalidDataException exception, HttpServletRequest request) {
        return ResponseEntity.status( HttpStatus.BAD_REQUEST).body( exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> wsException(Exception exception, HttpServletRequest request) {
        GlobalLogger.log(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something wrong happened");
    }
}
