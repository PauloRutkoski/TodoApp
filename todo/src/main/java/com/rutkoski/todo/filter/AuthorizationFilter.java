package com.rutkoski.todo.filter;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rutkoski.todo.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final String AUTH_HEADER = "Authorization";

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            doFilter(request, response, filterChain);
        } catch (SignatureVerificationException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid Credentials");
        } catch (TokenExpiredException e) {
        	response.setStatus(HttpStatus.UNAUTHORIZED.value());
        	response.getWriter().write(e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(e.getMessage());
        }
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (checkExistsToken(request, response)) {
            DecodedJWT decodedToken = JwtUtils.getDecodedToken(request.getHeader(AUTH_HEADER));
            setUpSpringAuthentication(decodedToken);
        } else {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

    private void setUpSpringAuthentication(DecodedJWT decodedToken) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(decodedToken.getSubject(), null, new ArrayList<>());
        SecurityContext context = new SecurityContextImpl();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
    }

    private boolean checkExistsToken(HttpServletRequest request, HttpServletResponse response) {
        String authenticationHeader = request.getHeader(AUTH_HEADER);
        return authenticationHeader != null;
    }
}
