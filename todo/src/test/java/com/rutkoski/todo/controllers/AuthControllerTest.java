package com.rutkoski.todo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rutkoski.todo.domain.User;
import com.rutkoski.todo.service.AuthService;
import com.rutkoski.todo.to.CredentialsTO;
import com.rutkoski.todo.to.UserTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @MockBean
    private AuthService authService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void callServiceAuthenticateOnAuthentication() throws Exception{
        UserTO user = new UserTO("test", "123456");
        Mockito.when(authService.authenticate(ArgumentMatchers.any())).thenReturn(new CredentialsTO());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth")
                .content(objectMapper.writeValueAsString(new UserTO()))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        Mockito.verify(authService, Mockito.times(1)).authenticate(ArgumentMatchers.any());
    }

    @Test
    void callServiceRegisterOnRegister() throws Exception{
        Mockito.when(authService.register(ArgumentMatchers.any())).thenReturn(new User());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/register")
                .content(objectMapper.writeValueAsString(new UserTO()))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        Mockito.verify(authService, Mockito.times(1)).register(ArgumentMatchers.any());
    }

    @Test
    void callServiceRefreshOnRefresh() throws Exception{
        CredentialsTO credentialsTO = new CredentialsTO();
        Mockito.when(authService.refreshAuthorization(ArgumentMatchers.any())).thenReturn(credentialsTO);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/auth/refresh")
                .content(objectMapper.writeValueAsString(credentialsTO))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();

        Mockito.verify(authService, Mockito.times(1)).refreshAuthorization(ArgumentMatchers.any());
    }

}
