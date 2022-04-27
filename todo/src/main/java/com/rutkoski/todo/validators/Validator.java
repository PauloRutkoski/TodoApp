package com.rutkoski.todo.validators;

public interface Validator<T> {
    boolean isValid(T entity);
    void addError(String message);
    boolean hasErrors();
    void resetErrors();
    String getFormattedErrors();
}
