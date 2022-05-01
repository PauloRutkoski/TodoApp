package com.rutkoski.todo.validators;

import com.rutkoski.todo.model.Task;
import com.rutkoski.todo.utils.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TaskValidator extends BaseValidator<Task> {
    private Task task;

    @PostConstruct
    private void build(){
        resetErrors();
    }

    @Override
    public boolean isValid(Task entity) {
        task = entity;
        if (task == null) {
            addError("The task has no value");
            return false;
        }
        validate();
        return !hasErrors();
    }

    private void validate() {
        validateTitle();
        validateStatus();
        validatePosition();
        validateUser();
    }

    private void validateUser() {
        if (task.getUser() == null) {
            addError("Not a valid user");
        }
    }

    private void validatePosition() {
        if (task.getPosition() == null || task.getPosition() < 0L) {
            addError("A position value should be informed");
        }
    }

    private void validateTitle() {
        if (StringUtils.isBlank(task.getTitle())) {
            addError("A title should be informed");
        }
    }

    private void validateStatus() {
        if (task.getStatus() == null) {
            addError("The entity status was not informed");
        }
    }
}
