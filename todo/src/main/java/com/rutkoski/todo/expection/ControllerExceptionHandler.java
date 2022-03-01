package com.rutkoski.todo.expection;

import com.rutkoski.todo.to.MessageTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(WsException.class)
    public ResponseEntity<MessageTO> wsException(WsException exception, HttpServletRequest request) {
        MessageTO message = new MessageTO(Instant.now(), exception.getStatus().value(), exception.getMessage());
        return ResponseEntity.status(message.getStatus()).body(message);
    }
}
