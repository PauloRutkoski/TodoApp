package com.rutkoski.todo.validators;

public interface Validator<T> {
    boolean isValid(T entity);
    boolean hasErrors();
    void resetErrors();
    String getFormattedErrors();
}
