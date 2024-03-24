package org.wedding.adapter.out.persistence;

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
    int countTodosByCardId(int cardId);
}
