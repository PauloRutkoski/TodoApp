package com.rutkoski.todo.service;

import com.rutkoski.todo.model.User;
import com.rutkoski.todo.repository.UserRepository;
import com.rutkoski.todo.test.utils.MockTestBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

class UserServiceTest extends MockTestBean {
    @Autowired
    private UserService service;
    @MockBean
    private UserRepository repository;

    @Test
    void persistCallSaveMethod() {
        User entity = new User(1L, "Test", "123456");
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(entity);

        User returned = service.persist(new User());

        Mockito.verify(repository, Mockito.times(1)).save(ArgumentMatchers.any());
        Assertions.assertEquals(entity, returned);
    }

    @Test
    void findByUsernameExistingUser() {
        User entity = new User(1L, "Test", "123456");
        Mockito.when(repository.findByUsername(ArgumentMatchers.any())).thenReturn(Optional.of(entity));

        User returned = service.findByUsername("");

        Mockito.verify(repository, Mockito.times(1)).findByUsername(ArgumentMatchers.any());
        Assertions.assertEquals(entity, returned);
    }

    @Test
    void findByUsernameNotExistingUser() {
        Mockito.when(repository.findByUsername(ArgumentMatchers.any())).thenReturn(Optional.empty());

        User returned = service.findByUsername("");

        Mockito.verify(repository, Mockito.times(1)).findByUsername(ArgumentMatchers.any());
        Assertions.assertNull( returned);
    }
}