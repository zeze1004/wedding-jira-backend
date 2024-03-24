package org.wedding.application.port.out.repository;

import org.wedding.domain.todo.Todo;

public interface TodoRepository {

    int save(Todo todo);
    int countTodosByCardId(int cardId);

    /* TODO
      void changeTodoItem(int cardId, int todoId, String todoItem);
      void changeTodoCheckStatus(int cardId, int todoId, String todoCheckStatus);
      List<Todo> getAllTodos(int cardId);
      void deleteTodo(int cardId, int todoId);
     */
}
