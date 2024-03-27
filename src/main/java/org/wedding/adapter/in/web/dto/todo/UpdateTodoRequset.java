package org.wedding.adapter.in.web.dto.todo;

import org.wedding.domain.card.exception.CardError;
import org.wedding.domain.todo.TodoCheckStatus;
import org.wedding.domain.todo.exception.TodoError;

import jakarta.validation.constraints.PositiveOrZero;

public record UpdateTodoRequset (

    @PositiveOrZero(message = "CARD_ID_MUST_BE_POSITIVE", payload = CardError.class)
    int cardId,
    @PositiveOrZero(message = "TODO_ID_MUST_BE_POSITIVE", payload = TodoError.class)
    int todoId,
    String todoItem,
    TodoCheckStatus todoCheckStatus
) {

        public UpdateTodoRequset(int cardId, int todoId, String todoItem) {
            this(cardId, todoId, todoItem, null);
        }

        public UpdateTodoRequset(int cardId, int todoId, TodoCheckStatus todoCheckStatus) {
            this(cardId, todoId, null, todoCheckStatus);
        }
}
