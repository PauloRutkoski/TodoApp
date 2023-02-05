package com.rutkoski.todo.service;

import com.rutkoski.todo.enums.TaskStatus;
import com.rutkoski.todo.exception.CustomException;
import com.rutkoski.todo.exception.InvalidDataException;
import com.rutkoski.todo.model.Task;
import com.rutkoski.todo.repository.TaskRepository;
import com.rutkoski.todo.utils.Context;
import com.rutkoski.todo.validators.TaskValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;
    @Autowired
    private Context context;
    @Autowired
    private TaskValidator validator;

    private Long getUserId() {
        return context.getUser().getId();
    }

    public Task findById(Long id) {
        Optional<Task> entity = repository.findById(id);
        return entity.orElse(null);
    }
    
    public List<Task> findAllByUser() {
    	Sort sort = Sort.by(Order.desc("position"));
    	Task ex = new Task();
    	ex.setUser(context.getUser());
        return repository.findAll(Example.of(ex), sort);

    }

    public List<Task> findPaginatedOrderedByPosition(int page) {
    	Sort sort = Sort.by(Order.desc("position"));
        Page<Task> result = repository.findAllByUserId(getUserId(), PageRequest.of(page, 20, sort));
        return result.getContent();
    }

    public Long findNextPosition() {
        Long lastPosition = repository.findTopPositionByUserId(getUserId());
        if (lastPosition == null) {
            return 0L;
        }
        return lastPosition + 1L;
    }

    public Task persist(Task entity) throws CustomException {
        if (!validator.isValid(entity)) {
            throw new InvalidDataException(validator.getFormattedErrors());
        }
        return repository.save(entity);
    }

    public void updateStatus(Long id, TaskStatus status) {
        repository.updateStatus(id, status);
    }

    public void updateTitle(Long id, String title) {
        repository.updateTitle(id, title);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
