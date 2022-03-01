package com.rutkoski.todo.controllers;

import com.rutkoski.todo.domain.User;
import com.rutkoski.todo.service.AuthService;
import com.rutkoski.todo.test.utils.TestBean;
import com.rutkoski.todo.to.CredentialsTO;
import com.rutkoski.todo.to.UserTO;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class AuthControllerTest extends TestBean {
    @MockBean
    private AuthService authService;

    private final String AUTH = "/auth";
    private final String REGISTER = AUTH + "/register";
    private final String REFRESH = AUTH + "/refresh";

    @Test
    void callAuthenticationSuccess() throws Exception {
        Mockito.when(authService.authenticate(ArgumentMatchers.any())).thenReturn(new CredentialsTO());
        MvcResult result = executePost(AUTH, new UserTO());

        Mockito.verify(authService, Mockito.times(1)).authenticate(ArgumentMatchers.any());
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void callRegisterSuccess() throws Exception {
        Mockito.when(authService.register(ArgumentMatchers.any())).thenReturn(new User());
        MvcResult result = executePost(REGISTER, new UserTO());

        Mockito.verify(authService, Mockito.times(1)).register(ArgumentMatchers.any());
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
    }

    @Test
    void callRefreshSuccess() throws Exception {
        Mockito.when(authService.refreshAuthorization(ArgumentMatchers.any())).thenReturn(new CredentialsTO());
        MvcResult result = executePost(REFRESH, "Token");

        Mockito.verify(authService, Mockito.times(1)).refreshAuthorization(ArgumentMatchers.any());
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

}
