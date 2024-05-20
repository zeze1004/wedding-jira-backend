package org.wedding.adapter.in.web.dto.todo;

import org.wedding.domain.card.exception.CardError;
import org.wedding.domain.todo.exception.TodoError;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record DeleteTodoRequest(

    @NotNull
    @PositiveOrZero(message = "CARD_ID_MUST_BE_POSITIVE", payload = CardError.class)
    int cardId,
    @NotNull
    @PositiveOrZero(message = "TODO_ID_MUST_BE_POSITIVE", payload = TodoError.class)
    int todoId
) {
}
