package org.wedding.application.port.in.command.todo;

import java.util.Optional;

import org.wedding.application.port.in.command.card.HasCardId;
import org.wedding.domain.todo.TodoCheckStatus;

import jakarta.validation.constraints.NotNull;

public record UpdateTodoCommand (

    @NotNull
    int cardId,
    @NotNull
    int todoId,
    Optional<String> todoItem,
    Optional<TodoCheckStatus> todoCheckStatus
) implements HasCardId {

        public UpdateTodoCommand(int cardId, int todoId, String todoItem, TodoCheckStatus todoCheckStatus) {
            this(cardId, todoId, Optional.ofNullable(todoItem), Optional.ofNullable(todoCheckStatus));
        }
}
