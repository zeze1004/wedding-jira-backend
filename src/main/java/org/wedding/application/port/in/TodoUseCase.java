package org.wedding.application.port.in;

import org.wedding.application.port.in.command.todo.CreateTodoCommand;
import org.wedding.application.port.in.command.todo.DeleteTodoCommand;
import org.wedding.application.port.in.command.todo.UpdateTodoCommand;

public interface TodoUseCase {
    void createTodo(CreateTodoCommand command);
    void updateTodo(UpdateTodoCommand command);
    void deleteTodo(DeleteTodoCommand command);

    /* TODO
      List<Todo> getAllTodos(int cardId);
     */
}
