package org.wedding.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.application.port.in.TodoUseCase;
import org.wedding.application.port.in.command.todo.CreateTodoCommand;
import org.wedding.application.port.in.command.todo.UpdateTodoCommand;
import org.wedding.application.port.out.repository.TodoRepository;
import org.wedding.config.anotation.CheckCardExistence;
import org.wedding.domain.todo.Todo;
import org.wedding.domain.todo.exception.TodoError;
import org.wedding.domain.todo.exception.TodoException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService implements TodoUseCase {

    static final int MAX_TODOS_PER_CARD = 3;

    private final TodoRepository todoRepository;

    @Transactional
    @CheckCardExistence
    @Override
    public void createTodo(CreateTodoCommand command) {

        checkTodoMaxCount(command.cardId());
        Todo todo = CreateTodoCommand.toEntity(command);

        todoRepository.save(todo);
    }

    @Transactional
    @CheckCardExistence
    @Override
    public void updateTodo(UpdateTodoCommand command) {

        checkTodoExistence(command.cardId(), command.todoId());
        Todo todo = todoRepository.findByTodoId(command.cardId(), command.todoId());

        Todo updatedTodo =
            todo.updateTodoItem(command.todoItem().orElse(todo.getTodoItem()))
                .updateTodoCheckStatus(command.todoCheckStatus().orElse(todo.getTodoCheckStatus()));

        todoRepository.update(updatedTodo);
    }

    @Transactional(readOnly = true)
    public void checkTodoMaxCount(int cardId) {

        if (todoRepository.countTodoByCardId(cardId) >= MAX_TODOS_PER_CARD) {
            throw new TodoException(TodoError.TODO_MAX_NUMBER_EXCEEDED);
        }
    }

    @Transactional(readOnly = true)
    public void checkTodoExistence(int cardId, int todoId) {

        if (!todoRepository.existsByTodoId(cardId, todoId)) {
            throw new TodoException(TodoError.TODO_NOT_FOUND);
        }
    }
}
