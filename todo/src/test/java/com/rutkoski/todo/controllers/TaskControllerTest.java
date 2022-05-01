package com.rutkoski.todo.controllers;

import com.rutkoski.todo.enums.TaskStatus;
import com.rutkoski.todo.exception.InvalidDataException;
import com.rutkoski.todo.model.Task;
import com.rutkoski.todo.model.User;
import com.rutkoski.todo.service.TaskService;
import com.rutkoski.todo.test.utils.RestTestBean;
import com.rutkoski.todo.to.TaskTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

class TaskControllerTest extends RestTestBean {
    @MockBean
    private TaskService service;

    private TaskTO getModelTaskTO() {
        return new TaskTO(1L, "Title", 1L, TaskStatus.UNSELECTED);
    }

    private Task getModelTask() {
        return new Task(1L, "Title", 1L, TaskStatus.UNSELECTED, getModelUser());
    }

    private User getModelUser() {
        return new User(1L, "test", "123456");
    }

    @Test
    void findById_TaskNotFound_ResponseNotFound() throws Exception {
        Mockito.when(service.findById(any())).thenReturn(null);

        MvcResult result = executeGet("/tasks/1");

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
        Mockito.verify(service).findById(1L);
    }

    @Test
    void findById_ExistingEntity_ReturnEntityTO() throws Exception {
        Mockito.when(service.findById(any())).thenReturn(getModelTask());

        MvcResult result = executeGet("/tasks/1");

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assertions.assertEquals(getModelTaskTO(), decodeBody(result, TaskTO.class));
        Mockito.verify(service).findById(1L);
    }

    @Test
    void findPaginated_NonexistentPage_ReturnNoContent() throws Exception {
        Mockito.when(service.findPaginatedOrderedByPosition(anyInt())).thenReturn(new ArrayList<>());

        MvcResult result = executeGet("/tasks/paginated/0");

        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
        Assertions.assertEquals("", result.getResponse().getContentAsString());
        Mockito.verify(service).findPaginatedOrderedByPosition(0);
    }

    @Test
    void findPaginated_ExistentPage_ReturnTOList() throws Exception {
        List<Task> tasks = Arrays.asList(getModelTask(), getModelTask());
        List<TaskTO> tasksTO = Arrays.asList(getModelTaskTO(), getModelTaskTO());
        Mockito.when(service.findPaginatedOrderedByPosition(anyInt())).thenReturn(tasks);

        MvcResult result = executeGet("/tasks/paginated/0");

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assertions.assertEquals(tasksTO,Arrays.asList(decodeBody(result, TaskTO[].class)));
        Mockito.verify(service).findPaginatedOrderedByPosition(0);
    }


    @Test
    void insert_ValidTitle_ReturnCreatedAndInsertedEntity() throws Exception {
        Task entity = getModelTask();
        TaskTO taskTO = getModelTaskTO();
        String title= entity.getTitle();
        Mockito.when(service.persist(ArgumentMatchers.any())).thenReturn(entity);

        MvcResult result = executePost("/tasks", title);

        Mockito.verify(service).persist(ArgumentMatchers.any());
        Assertions.assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
        Assertions.assertEquals(taskTO, decodeBody(result, TaskTO.class));
    }

    @Test
    void insert_InvalidTitle_ReturnBadRequestAndMessage() throws Exception {
        String title= " ";
        Mockito.when(service.persist(ArgumentMatchers.any())).thenThrow(InvalidDataException.class);

        MvcResult result = executePost("/tasks", title);

        Mockito.verify(service).persist(ArgumentMatchers.any());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    void updateStatus_StatusWithValue_ReturnOk() throws Exception  {
        MvcResult result = executePatch("/tasks/status/1", TaskStatus.UNSELECTED);

        Mockito.verify(service).updateStatus(1L,TaskStatus.UNSELECTED);
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void updateTitle_TitleBlank_ReturnBadRequest() throws Exception  {
        String title=  "  ";
        MvcResult result = executePatch("/tasks/title/1", title);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        Assertions.assertNotEquals("",  result.getResponse().getContentAsString());
    }

    @Test
    void updateTitle_TitleWithValue_ReturnOk() throws Exception  {
        String title = "t1";
        MvcResult result = executePatch("/tasks/title/1", title);

        Mockito.verify(service).updateTitle(1L,title);
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    void delete_IdNotNull_ReturnOK() throws Exception {
        MvcResult result = executeDelete("/tasks/1");

        Mockito.verify(service).delete(1L);
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
}