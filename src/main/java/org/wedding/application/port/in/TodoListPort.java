package org.wedding.application.port.in;

import java.util.List;

import org.wedding.domain.Todo;

public interface TodoListPort {
    void addTodo(Todo todo);
    void modifyTodoItem(String todoId, String todoItem);
    void changeCheckStatus(String todoId);
    List<Todo> getAllTodos();
}
