package com.rutkoski.todo.filter;

import com.rutkoski.todo.model.User;
import com.rutkoski.todo.service.UserService;
import com.rutkoski.todo.utils.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class ContextFilter extends OncePerRequestFilter {
    @Autowired
    private UserService service;
    @Autowired
    private Context context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            setupContext(auth);
        }
        super.doFilter(request, response, filterChain);
    }

    private void setupContext(Authentication auth) {
        String username = (String) auth.getPrincipal();
        User user = service.findByUsername(username);
        context.setUser(user);
    }
}
