package com.rutkoski.todo.utils;

import com.rutkoski.todo.test.utils.BasicTestBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringUtilsTest extends BasicTestBean {
    @Test
    void isBlank_NullString_ReturnTrue() {
        Assertions.assertTrue(StringUtils.isBlank(null));
    }

    @Test
    void isBlank_EmptyString_ReturnTrue() {
        Assertions.assertTrue(StringUtils.isBlank(""));
    }

    @Test
    void isBlank_EmptyStringWithSpaces_ReturnTrue() {
        Assertions.assertTrue(StringUtils.isBlank("   "));
    }

    @Test
    void isBlank_FilledString_ReturnTrue() {
        Assertions.assertFalse(StringUtils.isBlank("T"));
    }
}