package org.wedding.adapter.in.web.dto.todo;

import org.wedding.application.service.response.todo.TodoDto;
import org.wedding.domain.todo.TodoCheckStatus;

public record TodoResponse(

    int cardId,
    int todoId,
    String todoItem,
    TodoCheckStatus todoCheckStatus
) {

    public static TodoResponse fromEntity(TodoDto todoDto) {
        return new TodoResponse(todoDto.cardId(), todoDto.todoId(), todoDto.todoItem(), todoDto.todoCheckStatus());
    }
}
