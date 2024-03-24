package org.wedding.application.port.in;

import org.wedding.application.port.in.command.todo.CreateTodoCommand;

public interface TodoUseCase {
    void createTodo(CreateTodoCommand command);

    /* TODO
      void changeTodoItem(int cardId, int todoId, String todoItem);
      void changeTodoCheckStatus(int cardId, int todoId, TodoCheckStatus todoCheckStatus);
      List<Todo> getAllTodos(int cardId);
      void deleteTodo(int cardId, int todoId);
     */
}
