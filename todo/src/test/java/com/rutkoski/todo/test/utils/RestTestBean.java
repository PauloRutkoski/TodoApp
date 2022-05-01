package com.rutkoski.todo.test.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Getter
public abstract class RestTestBean extends MockTestBean {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    protected MvcResult executePost(String path, Object body) throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post(path)
                .content(getObjectMapper().writeValueAsString(body))
                .contentType(MediaType.APPLICATION_JSON);
        return getMockMvc().perform(request).andReturn();
    }
}
