package com.rutkoski.todo.test.utils;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


@AutoConfigureMockMvc(addFilters = false)
public abstract class MockTestBean extends BasicTestBean{
}
