package com.rutkoski.todo.validators;

import com.rutkoski.todo.test.utils.BasicTestBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class BaseValidatorTest extends BasicTestBean {
    BaseValidator<?> validator;

    @BeforeEach
    private void setup() {
        validator = Mockito.mock(BaseValidator.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    void addError_MessageNull_DoNotAdd() {
        validator.addError(null);

        Assertions.assertEquals(new ArrayList<>(), validator.errors);
    }

    @Test
    void addError_MessageEmpty_DoNotAdd() {
        validator.addError(" ");

        Assertions.assertEquals(new ArrayList<>(), validator.errors);
    }

    @Test
    void addError_MessageFilled_DoNotAdd() {
        String message= "MESSAGE TEST";

        validator.addError(message);

        Assertions.assertEquals(Collections.singletonList(message), validator.errors);
    }

    @Test
    void resetErrors_FilledErrors_CreateNewList() {
        validator.errors = Arrays.asList("T1","T2");

        validator.resetErrors();

        Assertions.assertEquals(new ArrayList<>(), validator.errors);
    }

    @Test
    void hasErrors_FillerErrors_ReturnTrue() {
        validator.errors = Arrays.asList("T1","T2");

        Assertions.assertTrue(validator.hasErrors());
    }

    @Test
    void hasErrors_EmptyErrors_ReturnFalse() {
        validator.errors = new ArrayList<>();

        Assertions.assertFalse(validator.hasErrors());
    }

    @Test
    void getFormattedErrors_ErrorsNull_ReturnEmptyString() {
        validator.errors = null;

        String message = validator.getFormattedErrors();

        Assertions.assertEquals("", message);
    }

    @Test
    void getFormattedErrors_ErrorsEmpty_ReturnEmptyString() {
        validator.errors = null;

        String message = validator.getFormattedErrors();

        Assertions.assertEquals("", message);
    }

    @Test
    void getFormattedErrors_ErrorsFilled_ReturnFormattedString() {
        validator.errors = Arrays.asList("T1","T2");

        String message = validator.getFormattedErrors();

        String expected = "T1\nT2\n";
        Assertions.assertEquals(expected, message);
    }
}