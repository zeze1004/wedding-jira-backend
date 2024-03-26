package org.wedding.config.aspect;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.application.port.in.command.todo.CreateTodoCommand;
import org.wedding.application.service.CardService;
import org.wedding.application.service.TodoService;
import org.wedding.domain.card.exception.CardError;
import org.wedding.domain.card.exception.CardException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class CardCheckAspectTest {

    @Autowired
    private TodoService todoService;
    @MockBean
    private CardService cardService;

    @DisplayName("카드가 존재하지 않을 투두 생성시 예외 발생")
    @Test
    public void createTodo_Fail() {
        // given
        doThrow(new CardException(CardError.CARD_NOT_FOUND)).when(cardService).checkCardExistence(99999);

        // when & then
        assertThrows(CardException.class, () -> {
            todoService.createTodo(new CreateTodoCommand(99999, "투두 생성 실패"));
        });
    }

    @Test
    public void createTodo_Succeed() {
        // given
        doNothing().when(cardService).checkCardExistence(14);

        // when & then
        assertDoesNotThrow(() -> {
            todoService.createTodo(new CreateTodoCommand(14, "투두 생성 성공"));
        });
    }
}
