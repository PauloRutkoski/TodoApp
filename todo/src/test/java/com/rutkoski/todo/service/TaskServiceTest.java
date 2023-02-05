package com.rutkoski.todo.service;

import com.rutkoski.todo.enums.TaskStatus;
import com.rutkoski.todo.exception.CustomException;
import com.rutkoski.todo.exception.InvalidDataException;
import com.rutkoski.todo.model.Task;
import com.rutkoski.todo.model.User;
import com.rutkoski.todo.repository.TaskRepository;
import com.rutkoski.todo.test.utils.MockTestBean;
import com.rutkoski.todo.utils.Context;
import com.rutkoski.todo.validators.TaskValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

class TaskServiceTest extends MockTestBean {
    @Autowired
    private TaskService service;
    @Autowired
    private Context context;
    @MockBean
    private TaskRepository repository;
    @MockBean
    private TaskValidator validator;

    private Task getModelTask() {
        return new Task(1L, "Title", 1L, TaskStatus.UNDONE, getModelUser());
    }

    private User getModelUser() {
        return new User(1L, "test", "123456");
    }

    private List<Task> generateList(int size) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            Task entity = getModelTask();
            entity.setId(null);
            tasks.add(entity);
        }
        return tasks;
    }

    @BeforeEach
    void setup() {
        context.setUser(getModelUser());
    }

    @Test
    void findById_NonexistentEntity_ReturnNull() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.empty());

        Task entity = service.findById(1L);

        Assertions.assertNull(entity);
        Mockito.verify(repository).findById(1L);
    }

    @Test
    void findById_ExistentEntity_ReturnEntity() {
        Task entity = getModelTask();
        Mockito.when(repository.findById(any())).thenReturn(Optional.of(entity));

        Task returned = service.findById(1L);

        Assertions.assertEquals(entity, returned);
        Mockito.verify(repository).findById(1L);
    }

    @Test
    void findPaginatedOrderedByPosition_WithGivenPageSortAndSize_ReturnList() {
        List<Task> expList = generateList(20);
        Page<Task> mockPage = Mockito.mock(Page.class);
        Mockito.when(mockPage.getContent()).thenReturn(expList);
        Mockito.when(repository.findAllByUserId(any(), any())).thenReturn(mockPage);

        List<Task> result = service.findPaginatedOrderedByPosition(1);

        Mockito.verify(repository).findAllByUserId(getModelUser().getId(), PageRequest.of(1, 20, Sort.by("position").descending()));
        Assertions.assertEquals(expList, result);
    }

    @Test
    void findNextPosition_LastUserTaskNotFound_ReturnZero() {
        Mockito.when(repository.findTopPositionByUserId(any())).thenReturn(null);

        Long result = service.findNextPosition();

        Mockito.verify(repository).findTopPositionByUserId(context.getUser().getId());
        Assertions.assertEquals(0L, result);
    }

    @Test
    void findNextPosition_LastUserTaskFound_ReturnLastPositionPlusOne() {
        Mockito.when(repository.findTopPositionByUserId(any())).thenReturn(1L);

        Long result = service.findNextPosition();

        Mockito.verify(repository).findTopPositionByUserId(context.getUser().getId());
        Assertions.assertEquals(2L, result);
    }

    @Test
    void persist_InvalidTask_ThrowInvalidDataException() {
        Task entity = getModelTask();
        String errorMessage = "Error Message";
        Mockito.when(validator.isValid(any())).thenReturn(false);
        Mockito.when(validator.getFormattedErrors()).thenReturn(errorMessage);

        Assertions.assertThrows(InvalidDataException.class, () -> service.persist(entity));
    }

    @Test
    void persist_ValidTask_PersistAndReturnTask() throws CustomException {
        Task entity = getModelTask();
        Mockito.when(validator.isValid(any())).thenReturn(true);
        Mockito.when(repository.save(any())).thenReturn(entity);

        Task result = service.persist(entity);

        Mockito.verify(repository).save(entity);
        Assertions.assertEquals(entity, result);
    }

    @Test
    void updateStatus_ValidParameters_CallUpdateStatus() {
        service.updateStatus(1L, TaskStatus.UNDONE);

        Mockito.verify(repository).updateStatus(1L, TaskStatus.UNDONE);
    }

    @Test
    void updateTitle_ValidParameters_CallUpdateTitle() {
        service.updateTitle(1L, "T");

        Mockito.verify(repository).updateTitle(1L, "T");
    }

    @Test
    void delete() {
        service.delete(1L);

        Mockito.verify(repository).deleteById(1L);
    }
}