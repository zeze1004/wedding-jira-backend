package org.wedding.adapter.in.web.dto.todo;

import org.wedding.domain.card.exception.CardError;
import org.wedding.domain.todo.exception.TodoError;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateTodoRequest (

    @PositiveOrZero(message = "CARD_ID_MUST_BE_POSITIVE", payload = CardError.class)
    int cardId,
    @NotBlank(message = "TODO_ITEM_IS_REQUIRED", payload = TodoError.class)
    @Size(min = 2, max = 30, message = "TODO_ITEM_SIZE_NOT_VALID", payload = TodoError.class)
    String todoItem
) {

    public CreateTodoRequest(int cardId, String todoItem) {
        this.cardId = cardId;
        this.todoItem = todoItem;
    }

    public String toString() {
        return """
            CreateTodoRequest{
                todoItem='%s',
                cardId='%s'
            }
            """.formatted(todoItem, cardId);
    }
}
