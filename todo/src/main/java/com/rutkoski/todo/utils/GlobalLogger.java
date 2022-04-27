package com.rutkoski.todo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GlobalLogger {
    private static final Logger log = LoggerFactory.getLogger(GlobalLogger.class);

    public static void log(Throwable t) {
        log.error(buildMessage(), t);
    }

    private static String buildMessage() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String msg = LocalDateTime.now().format(formatter);
        String username = getUsernameMsg();
        msg += username;
        return msg;
    }

    private static String getUsernameMsg() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return " - " + auth.getName();
        }
        return "";
    }
}
