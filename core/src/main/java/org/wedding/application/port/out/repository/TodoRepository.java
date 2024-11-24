package org.wedding.application.port.out.repository;

import java.util.ArrayList;

import org.wedding.domain.todo.Todo;

public interface TodoRepository {

    int save(Todo todo);

    int countTodoByCardId(int cardId);

    void update(Todo todo);

    Todo findByTodoId(int cardId, int todoId);

    boolean existsByTodoId(int cardId, int todoId);

    void deleteTodo(int cardId, int todoId);

    ArrayList<Todo> getAllTodos(int cardId);
}
