package com.rutkoski.todo.validators;

import com.rutkoski.todo.enums.TaskStatus;
import com.rutkoski.todo.model.Task;
import com.rutkoski.todo.model.User;
import com.rutkoski.todo.test.utils.BasicTestBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskValidatorTest extends BasicTestBean {
    @Autowired
    private TaskValidator validator;

    private Task getModelTask(){
        return new Task(1L, "Title", 1L, TaskStatus.UNDONE, getModelUser());
    }

    private User getModelUser(){
        return new User(1L, "test", "123456");
    }

    @BeforeEach
    void setUp() {
        validator.resetErrors();
    }


    @Test
    void resetErrors_ErrorsNotEmpty_CleanErrors() {
        validator.addError("testError");

        validator.resetErrors();

        assertEquals(new ArrayList<>(), validator.errors);
    }

    @Test
    void hasErrors_ErrorsNotEmpty_ReturnTrue() {
        String testError = "TESTE ERROR";

        validator.addError(testError);

        assertTrue(validator.hasErrors());
    }

    @Test
    void hasErrors_ErrorsIsEmpty_ReturnFalse() {
        validator.resetErrors();

        assertFalse(validator.hasErrors());
    }

    @Test
    void isValid_ErrorsAreFilled_ResetErrorsBeforeStart() {
        boolean isValid = validator.isValid(null);
        isValid = validator.isValid(getModelTask());

        assertFalse(validator.hasErrors());
        assertTrue(isValid);
    }

    @Test
    void isValid_EntityNull_ReturnFalseAndFillErrors() {
        boolean isValid = validator.isValid(null);

        assertTrue(validator.hasErrors());
        assertFalse(isValid);
    }

    @Test
    void isValid_TitleNull_ReturnFalseAndFillErrors() {
        Task entity = getModelTask();
        entity.setTitle(null);

        boolean isValid = validator.isValid(entity);

        assertTrue(validator.hasErrors());
        assertEquals(1, validator.errors.size());
        assertFalse(isValid);
    }

    @Test
    void isValid_TitleBlank_ReturnFalseAndFillErrors() {
        Task entity = getModelTask();
        entity.setTitle(" ");

        boolean isValid = validator.isValid(entity);

        assertTrue(validator.hasErrors());
        assertEquals(1, validator.errors.size());
        assertFalse(isValid);
    }

    @Test
    void isValid_StatusNull_ReturnFalseAndFillErrors() {
        Task entity = getModelTask();
        entity.setStatus(null);

        boolean isValid = validator.isValid(entity);

        assertTrue(validator.hasErrors());
        assertEquals(1, validator.errors.size());
        assertFalse(isValid);
    }

    @Test
    void isValid_PositionNull_ReturnFalseAndFillErrors() {
        Task entity = getModelTask();
        entity.setPosition(null);

        boolean isValid = validator.isValid(entity);

        assertTrue(validator.hasErrors());
        assertEquals(1, validator.errors.size());
        assertFalse(isValid);
    }

    @Test
    void isValid_PositionSmallerThanZero_ReturnFalseAndFillErrors() {
        Task entity = getModelTask();
        entity.setPosition(-1L);

        boolean isValid = validator.isValid(entity);

        assertTrue(validator.hasErrors());
        assertEquals(1, validator.errors.size());
        assertFalse(isValid);
    }

    @Test
    void isValid_UserNull_ReturnFalseAndFillErrors() {
        Task entity = getModelTask();
        entity.setUser(null);

        boolean isValid = validator.isValid(entity);

        assertTrue(validator.hasErrors());
        assertEquals(1, validator.errors.size());
        assertFalse(isValid);
    }

    @Test
    void isValid_ValidAttributes_ReturnTrueWithoutErrors() {
        Task entity = getModelTask();

        boolean isValid = validator.isValid(entity);

        assertFalse(validator.hasErrors());
        assertEquals(0, validator.errors.size());
        assertTrue(isValid);
    }
}