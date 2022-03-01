package com.rutkoski.todo.repository;

import com.rutkoski.todo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    public Optional<User> findByUsername(String username);
}
