package org.wedding.application.port.in.command.todo;

import org.wedding.application.port.in.command.card.HasCardId;
import org.wedding.domain.todo.Todo;
import org.wedding.domain.todo.TodoCheckStatus;

import jakarta.validation.constraints.NotNull;

public record CreateTodoCommand(@NotNull int cardId, @NotNull String todoItem) implements HasCardId {

    public static Todo toEntity(CreateTodoCommand createTodoCommand) {
        return Todo.builder()
            .cardId(createTodoCommand.cardId())
            .todoItem(createTodoCommand.todoItem())
            .todoCheckStatus(TodoCheckStatus.UNCHECKED)
            .build();
    }

    public String toString() {
        return """
            CreateTodoCommand{
                cardId='%s',
                todoItem='%s'
            }
            """.formatted(cardId, todoItem);
    }
}
