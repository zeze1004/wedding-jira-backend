package org.wedding.application.port.in;

import org.wedding.application.port.in.command.todo.CreateTodoCommand;

public interface TodoUseCase {
    void createTodo(CreateTodoCommand command);
      void updateTodoItem(UpdateTodoCommand command);
      void updateTodoCheckStatus(UpdateTodoCommand command);

    /* TODO
      List<Todo> getAllTodos(int cardId);
      void deleteTodo(int cardId, int todoId);
     */
}
