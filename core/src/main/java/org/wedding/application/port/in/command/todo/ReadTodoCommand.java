package org.wedding.application.port.in.command.todo;

import org.wedding.application.port.in.command.card.HasCardId;

import jakarta.validation.constraints.NotNull;

public record ReadTodoCommand(@NotNull int cardId, @NotNull int todoId) implements HasCardId {
}
