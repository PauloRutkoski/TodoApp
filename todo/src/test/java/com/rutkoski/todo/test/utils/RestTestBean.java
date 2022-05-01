package com.rutkoski.todo.test.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;

@Getter
public abstract class RestTestBean extends MockTestBean {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    protected MvcResult executeGet(String path) throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .get(path)
                .contentType(MediaType.APPLICATION_JSON);
        return getMockMvc().perform(request).andReturn();
    }

    protected MvcResult executePost(String path, Object body) throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post(path)
                .content(encodeBody(body))
                .contentType(MediaType.APPLICATION_JSON);
        return getMockMvc().perform(request).andReturn();
    }

    protected MvcResult executePatch(String path, Object body) throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .patch(path)
                .content(encodeBody(body))
                .contentType(MediaType.APPLICATION_JSON);
        return getMockMvc().perform(request).andReturn();
    }

    protected MvcResult executeDelete(String path) throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .delete(path)
                .contentType(MediaType.APPLICATION_JSON);
        return getMockMvc().perform(request).andReturn();
    }

    protected <T> T decodeBody(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    }

    protected String encodeBody(Object obj) throws JsonProcessingException {
        if(obj instanceof String){
            return (String) obj;
        }
        return objectMapper.writeValueAsString(obj);
    }
}
