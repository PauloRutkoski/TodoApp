package com.rutkoski.todo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rutkoski.todo.enums.TaskStatus;
import com.rutkoski.todo.exception.CustomException;
import com.rutkoski.todo.exception.InvalidDataException;
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

import javax.sound.midi.InvalidMidiDataException;

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
    
    @GetMapping
    public ResponseEntity<List<TaskTO>> findAll() {
        List<Task> list = service.findAllByUser();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<TaskTO> toList = list.stream().map(this::encode).collect(Collectors.toList());
        return ResponseEntity.ok(toList);
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

    @PostMapping(consumes = "application/json")
    public ResponseEntity<TaskTO> insert(@RequestBody String title) throws CustomException {
    	Task entity = new Task(null, decodeJsonString(title), service.findNextPosition(), TaskStatus.UNDONE, context.getUser());
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
    public ResponseEntity<String> updateTitle(@PathVariable Long id, @RequestBody String title) throws CustomException {
        if(StringUtils.isBlank(title)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A new title should be informed");
        }
        service.updateTitle(id, decodeJsonString(title));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    private TaskTO encode(Task entity) {
        return mapper.map(entity, TaskTO.class);
    }

    private String decodeJsonString(String json) throws InvalidDataException {
    	try {
	    	ObjectMapper mapper = new ObjectMapper();
	    	return  mapper.readValue(json, String.class);
    	}catch (Exception e) {
    		throw new InvalidDataException(e);
    	}
    }
}
