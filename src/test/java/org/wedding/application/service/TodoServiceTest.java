package org.wedding.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.wedding.application.port.in.command.todo.CreateTodoCommand;
import org.wedding.application.port.out.repository.TodoRepository;
import org.wedding.domain.card.exception.CardError;
import org.wedding.domain.card.exception.CardException;
import org.wedding.domain.todo.Todo;
import org.wedding.domain.todo.exception.TodoException;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;
    @Mock
    private TodoRepository todoRepository;
    @Mock
    private CardService cardService;
    private Todo todo;
    private CreateTodoCommand createCommand;

    @BeforeEach
    void setUp() {

        createCommand = new CreateTodoCommand(1, "할일");
        todo = CreateTodoCommand.toEntity(createCommand);
    }

    @DisplayName("cardId가 존재하고, 할일이 3개 미만일 때 할일 생성 성공")
    @Test
    void createTodo_Success() {

        doNothing().when(cardService).checkCardExistence(anyInt());
        when(todoRepository.countTodosByCardId(anyInt())).thenReturn(1);

        todoService.createTodo(createCommand);

        verify(todoRepository).save(any(Todo.class));
    }

    @DisplayName("cardId가 존재하고, 할일이 3개 이상일 때 할일 생성 실패")
    @Test
    void createTodo_Fail_WhenMaxTodoExceed_() {

        doNothing().when(cardService).checkCardExistence(anyInt());
        when(todoRepository.countTodosByCardId(anyInt())).thenReturn(TodoService.MAX_TODOS_PER_CARD);

        assertThrows(TodoException.class, () -> todoService.createTodo(createCommand));
    }

    @Test
    void createTodo_Fail_WhenCardDoesNotExist() {

        doThrow(new CardException(CardError.CARD_NOT_FOUND)).when(cardService).checkCardExistence(anyInt());
        assertThrows(CardException.class, () -> todoService.createTodo(createCommand));
    }
}
