package org.wedding.application.service.response.todo;

import org.wedding.domain.todo.Todo;
import org.wedding.domain.todo.TodoCheckStatus;

public record TodoDto(

    int cardId,
    int todoId,
    String todoItem,
    TodoCheckStatus todoCheckStatus
) {

    public static TodoDto fromEntity(Todo todo) {
        return new TodoDto(todo.getCardId(), todo.getTodoId(), todo.getTodoItem(), todo.getTodoCheckStatus());
    }
}
