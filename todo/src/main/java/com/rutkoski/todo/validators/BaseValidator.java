package com.rutkoski.todo.validators;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseValidator<T> implements Validator<T> {
    List<String> errors;

    @Override
    public void addError(String message) {
        if(errors == null){
            errors = new ArrayList<>();
        }
        if(message != null && !message.trim().isEmpty()){
            errors.add(message);
        }
    }

    @Override
    public void resetErrors() {
        errors = new ArrayList<>();
    }

    @Override
    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

    @Override
    public String getFormattedErrors() {
        if(errors == null || errors.isEmpty()){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        errors.forEach(it -> builder.append(it).append("\n"));
        return builder.toString();
    }
}
