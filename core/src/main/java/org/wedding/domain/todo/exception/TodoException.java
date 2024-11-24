package org.wedding.domain.todo.exception;

import org.wedding.common.exception.CommonError;
import org.wedding.common.exception.WeddingProjectException;

import lombok.Getter;

@Getter
public class TodoException extends WeddingProjectException {

    private final CommonError todoError;

    public TodoException(CommonError todoError) {
        super(todoError, todoError.getMessage());
        this.todoError = todoError;
    }
}
