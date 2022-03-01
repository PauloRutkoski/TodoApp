package com.rutkoski.todo.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rutkoski.todo.enums.TokenTypeEnum;
import com.rutkoski.todo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    private final String AUTH_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            doFilter(request, response, filterChain);
        } catch (SignatureVerificationException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid Credentials");
        } catch (Exception e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(e.getMessage());
        }
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (checkExistsToken(request, response)) {
            DecodedJWT decodedToken = jwtUtils.getDecodedToken(request.getHeader(AUTH_HEADER));
            setUpSpringAuthentication(decodedToken);
        } else {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

    private void setUpSpringAuthentication(DecodedJWT decodedToken) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(decodedToken.getSubject(), null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private boolean checkExistsToken(HttpServletRequest request, HttpServletResponse response) {
        String authenticationHeader = request.getHeader(AUTH_HEADER);
        return !(authenticationHeader == null);
    }
}
