package org.wedding.domain.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Todo {
    private final int todoId;
    private final String todoItem;
    private final TodoCheckStatus todoCheckStatus;
    private final int cardId;

    public Todo changeCheckStatus(TodoCheckStatus todoCheckStatus) {
        return new Todo(this.todoId, this.todoItem, todoCheckStatus, this.cardId);
    }

    public Todo changeTodoItem(String todoItem) {
        return new Todo(this.todoId, todoItem, this.todoCheckStatus, this.cardId);
    }

    public String toString() {
        return """
            Todo{
                todoId='%s',
                todoItem='%s',
                todoCheckStatus='%s'
                cardId='%s'
            }
            """.formatted(todoId, todoItem, todoCheckStatus, cardId);
    }
}
