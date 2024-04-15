package org.wedding.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.wedding.application.port.out.repository.CardBoardRepository;
import org.wedding.domain.cardboard.CardBoard;
import org.wedding.domain.cardboard.exception.CardBoardError;
import org.wedding.domain.cardboard.exception.CardBoardException;

@ExtendWith(MockitoExtension.class)
class CardBoardServiceTest {
    @InjectMocks
    private CardBoardService cardBoardUseCase;
    @Mock
    private CardBoardRepository cardBoardRepository;
    private CardBoard cardBoard;
    private int userId;
    private int cardId;

    @BeforeEach
    void setUp() {
        userId = 1;
        cardId = 100;
        List<Integer> cardIds = new ArrayList<>(Arrays.asList(1, 2, 3));
        cardBoard = new CardBoard(1, userId, cardIds);
    }

    @DisplayName("카드 저장시 카드보드에 신규 카드ID가 추가된다")
    @Test
    void addCardToCardBoard_Success() {
        // given
        when(cardBoardRepository.findCardBoardByUserId(userId)).thenReturn(cardBoard);

        // when
        cardBoardUseCase.addCardToCardBoard(cardId, userId);

        // then
        verify(cardBoardRepository, times(1)).findCardBoardByUserId(userId);
        verify(cardBoardRepository, times(1)).addCardIds(cardBoard.getCardBoardId(), cardId);
        assertEquals(4, cardBoard.getCardIds().size());
        assertEquals(1, cardBoard.getUserId());
        assertEquals(Arrays.asList(1, 2, 3, 100), Arrays.stream(cardBoard.getCardIds().toArray()).toList());
        // 신규 카드ID가 인덱스 마지막에 추가된다
        assertEquals(cardBoard.getCardIds().lastIndexOf(cardId), cardBoard.getCardIds().size() - 1);
    }

    @DisplayName("카드 저장시 카드보드가 없으면 예외를 발생시킨다")
    @Test
    void addCardToCardBoard_CardBoardNotFound() {
        when(cardBoardRepository.findCardBoardByUserId(userId)).thenReturn(null);

        CardBoardException exception = assertThrows(CardBoardException.class,
            () -> cardBoardUseCase.addCardToCardBoard(cardId, userId));

        assertEquals(CardBoardError.CARD_BOARD_NOT_FOUND, exception.getCardBoardError());
        verify(cardBoardRepository, times(1)).findCardBoardByUserId(userId);
        verify(cardBoardRepository, never()).addCardIds(anyInt(), anyInt());
    }

    @DisplayName("카드 소유자 확인: 카드보드에 카드ID가 존재하면 true를 반환한다")
    @Test
    void checkCardOwner_Success() {
        // given
        when(cardBoardRepository.findCardBoardByUserId(userId)).thenReturn(cardBoard);

        // when
        boolean result1 = cardBoardUseCase.checkCardOwner(userId, 1);
        boolean result2 = cardBoardUseCase.checkCardOwner(userId, 2);
        boolean result3 = cardBoardUseCase.checkCardOwner(userId, 3);

        // then
        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);
        verify(cardBoardRepository, times(3)).findCardBoardByUserId(userId);
    }

    @DisplayName("카드 소유자 확인: 카드보드에 카드ID가 존재하지 않으면 false를 반환한다")
    @Test
    void checkCardOwner_Fail() {
        // given
        int nonExistentCardId = 999;
        when(cardBoardRepository.findCardBoardByUserId(userId)).thenReturn(cardBoard);

        // when
        boolean result = cardBoardUseCase.checkCardOwner(userId, nonExistentCardId);

        // then
        assertFalse(result);
        verify(cardBoardRepository, times(1)).findCardBoardByUserId(userId);
    }
}
