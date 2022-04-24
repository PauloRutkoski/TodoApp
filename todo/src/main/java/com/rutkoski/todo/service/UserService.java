package com.rutkoski.todo.service;

import com.rutkoski.todo.model.User;
import com.rutkoski.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User persist(User entity){
        return repository.save(entity);
    }

    public User findByUsername(String username){
        Optional<User> entity = repository.findByUsername(username);
        return entity.orElse(null);
    }

}
