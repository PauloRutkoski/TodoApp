package com.rutkoski.todo.controllers;

import com.rutkoski.todo.enums.TaskStatus;
import com.rutkoski.todo.exception.CustomException;
import com.rutkoski.todo.model.Task;
import com.rutkoski.todo.service.TaskService;
import com.rutkoski.todo.to.TaskTO;
import com.rutkoski.todo.utils.Context;
import com.rutkoski.todo.utils.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService service;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private Context context;

    @GetMapping("/{id}")
    public ResponseEntity<TaskTO> findById(@PathVariable Long id) {
        Task entity = service.findById(id);
        if (entity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(encode(entity));
    }

    @GetMapping("/paginated/{page}")
    public ResponseEntity<List<TaskTO>> findPaginated(@PathVariable int page) {
        List<Task> list = service.findPaginatedOrderedByPosition(page);
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<TaskTO> toList = list.stream().map(this::encode).collect(Collectors.toList());
        return ResponseEntity.ok(toList);
    }

    @PostMapping
    public ResponseEntity<TaskTO> insert(@RequestBody String title) throws CustomException {
        Task entity = new Task(null, title, service.findNextPosition(), TaskStatus.UNSELECTED, context.getUser());
        entity = service.persist(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(encode(entity));
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody TaskStatus status) {
        if(status == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A new status should be informed");
        }
        service.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/title/{id}")
    public ResponseEntity<String> updateTitle(@PathVariable Long id, @RequestBody String title) {
        if(StringUtils.isBlank(title)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A new title should be informed");
        }
        service.updateTitle(id, title);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> updateTitle(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    private TaskTO encode(Task entity) {
        return mapper.map(entity, TaskTO.class);
    }

}
