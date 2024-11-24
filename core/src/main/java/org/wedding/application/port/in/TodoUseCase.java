package org.wedding.application.port.in;

import java.util.ArrayList;

import org.wedding.application.port.in.command.todo.CreateTodoCommand;
import org.wedding.application.port.in.command.todo.DeleteTodoCommand;
import org.wedding.application.port.in.command.todo.ReadTodoCommand;
import org.wedding.application.port.in.command.todo.UpdateTodoCommand;
import org.wedding.application.service.response.todo.TodoDto;

public interface TodoUseCase {

    void createTodo(CreateTodoCommand command);

    void updateTodo(UpdateTodoCommand command);

    void deleteTodo(DeleteTodoCommand command);

    ArrayList<TodoDto> getAllTodos(int cardId);

    TodoDto readTodo(ReadTodoCommand command);
}
