package org.wedding.application.port.in;

import org.wedding.application.port.in.command.todo.CreateTodoCommand;
import org.wedding.application.port.in.command.todo.UpdateTodoCommand;

public interface TodoUseCase {
    void createTodo(CreateTodoCommand command);
    void updateTodo(UpdateTodoCommand command);

    /* TODO
      List<Todo> getAllTodos(int cardId);
      void deleteTodo(int cardId, int todoId);
     */
}
