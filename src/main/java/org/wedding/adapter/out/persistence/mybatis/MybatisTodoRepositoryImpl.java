package org.wedding.adapter.out.persistence.mybatis;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.wedding.application.port.out.repository.TodoRepository;
import org.wedding.domain.todo.Todo;

@Repository
@Mapper
public interface MybatisTodoRepositoryImpl extends TodoRepository {

    @Override
    int save(Todo todo);
    @Override
    int countTodoByCardId(int cardId);
    @Override
    void update(Todo todo);
    @Override
    Todo findByTodoId(int cardId, int todoId);
    @Override
    boolean existsByTodoId(int cardId, int todoId);
    @Override
    void deleteTodo(int cardId, int todoId);
    @Override
    ArrayList<Todo> getAllTodos(int cardId);
}
