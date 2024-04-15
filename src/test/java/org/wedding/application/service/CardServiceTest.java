package org.wedding.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.wedding.application.port.in.CardBoardUseCase;
import org.wedding.application.port.in.command.card.CreateCardCommand;
import org.wedding.application.port.in.command.card.ModifyCardCommand;
import org.wedding.application.port.out.repository.CardRepository;
import org.wedding.application.service.response.card.ReadCardResponse;
import org.wedding.domain.CardStatus;
import org.wedding.domain.card.Card;
import org.wedding.domain.card.event.CardCreatedEvent;
import org.wedding.domain.card.exception.CardError;
import org.wedding.domain.card.exception.CardException;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @InjectMocks
    private CardService cardService;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private CardBoardUseCase cardBoardUseCase;

    private Card card;
    private CreateCardCommand createCommand;
    private final int userId = 1;
    private List<Card> cards;

    @BeforeEach
    void setUp() {
        createCommand = new CreateCardCommand(
            userId,
            "스드메 예약금 넣기",
            100000L,
            null
        );

        cardService = new CardService(cardRepository, eventPublisher, cardBoardUseCase);
        card = CreateCardCommand.toEntity(createCommand);

        cards = Arrays.asList(
            new Card(1, "Card 1", 1000, LocalDate.of(2023, 6, 30), CardStatus.BACKLOG),
            new Card(2, "Card 2", 2000, LocalDate.of(2023, 7, 15), CardStatus.PROGRESS),
            new Card(3, "Card 3", 3000, LocalDate.of(2023, 8, 31), CardStatus.DONE)
        );
    }

    @DisplayName("카드 제목과 예산 그리고 마감일를 넣으면 카드 생성 성공")
    @Test
    void createCardWithAllValidPath() {

        // given
        CreateCardCommand allValidCreateCardCommand = new CreateCardCommand(
            userId,
            "스드메 예약금 넣기",
            100000L,
            LocalDate.now()
        );
        when(cardRepository.existsByCardTitle(allValidCreateCardCommand.cardTitle())).thenReturn(false);

        // when
        cardService.createCard(createCommand);

        // then
        verify(cardRepository, times(1)).save(any());
        verify(eventPublisher, times(1)).publishEvent(any(CardCreatedEvent.class));
    }

    @DisplayName("카드 제목만 넣고 예산, 마감일을 넣지 않아도 카드 생성 성공")
    @Test
    void createCardWithOnlyTitle() {

        // given
        CreateCardCommand createCardWithOnlyTitleCommand = new CreateCardCommand(
            userId,
            "결혼식 리허설하기",
            0L,
            null
        );
        when(cardRepository.existsByCardTitle(createCardWithOnlyTitleCommand.cardTitle())).thenReturn(false);

        // when
        cardService.createCard(createCardWithOnlyTitleCommand);

        // then
        verify(cardRepository, times(1)).save(any());
        verify(eventPublisher, times(1)).publishEvent(any(CardCreatedEvent.class));
    }

    @DisplayName("카드 제목이 중복되면 카드 생성 실패")
    @Test
    void checkDuplicateCardTitle() {

        CreateCardCommand createCardDuplicateTitleRequest = new CreateCardCommand(
            userId,
            "스드메 예약금 넣기",
            0L,
            null
        );

        lenient().when(cardRepository.existsByCardTitle(createCardDuplicateTitleRequest.cardTitle())).thenReturn(true);

        try {
            cardService.checkDuplicateCardTitle(createCardDuplicateTitleRequest.cardTitle());
        } catch (CardException e) {
            assertEquals(CardError.CARD_TITLE_IS_DUPLICATED, CardError.of("CARD_TITLE_IS_DUPLICATED"));
        }
    }

    @DisplayName("카드가 존재한다면 카드 이름 수정을 성공")
    @Test
    void modifyCard_Title() {
        ModifyCardCommand modifyCardTitleCommand = new ModifyCardCommand(
            userId,
            "스드메 예약금 넣기 수정",
            0L,
            null,
            null
        );
        lenient().when(cardRepository.existsByCardId(card.getCardId())).thenReturn(true);
        lenient().when(cardRepository.existsByCardTitle(modifyCardTitleCommand.cardTitle().get())).thenReturn(false);
        lenient().when(cardRepository.findByCardId(card.getCardId())).thenReturn(card);
        when(cardBoardUseCase.checkCardOwner(userId, card.getCardId())).thenReturn(true);
        cardService.modifyCard(card.getCardId(), modifyCardTitleCommand);
        verify(cardRepository, times(1)).update(any());
    }

    @DisplayName("카드가 존재하지 않을 때 카드 이름 수정할시 실패")
    @Test
    void modifyCard_Title_Fail() {
        ModifyCardCommand modifyCardTitleCommand = new ModifyCardCommand(
            userId,
            "스드메 예약금 넣기 수정",
            0L,
            null,
            null
        );
        when(cardBoardUseCase.checkCardOwner(userId, card.getCardId())).thenReturn(false);

        assertThrows(CardException.class, () -> cardService.modifyCard(card.getCardId(), modifyCardTitleCommand));
    }

    @DisplayName("카드 상태 변경")
    @Test
    void modifyCard_Status() {

        ModifyCardCommand modifyCardStatusCommand = new ModifyCardCommand(
            userId,
            null,
            0L,
            null,
            CardStatus.PROGRESS
        );

        lenient().when(cardRepository.existsByCardId(card.getCardId())).thenReturn(true);
        assertThat(modifyCardStatusCommand.cardTitle()).isEqualTo(Optional.empty());
        lenient().when(cardRepository.findByCardId(card.getCardId())).thenReturn(card);
        when(cardBoardUseCase.checkCardOwner(userId, card.getCardId())).thenReturn(true);
        cardService.modifyCard(card.getCardId(), modifyCardStatusCommand);
        verify(cardRepository, times(1)).update(any());
    }


    @DisplayName("카드 제목에 따른 카드 조회")
    @Test
    void readCardsByCardTitle() {

        // given
        String cardTitle = "스드메 예약금 넣기";
        lenient().when(cardRepository.existsByCardTitle(cardTitle)).thenReturn(true);
        lenient().when(cardRepository.findByCardTitle(cardTitle)).thenReturn(card);

        // when
        ReadCardResponse actualCard = cardService.readCardsByCardTitle(cardTitle);

        // then
        verify(cardRepository, times(1)).findByCardTitle(cardTitle);
        assertThat(actualCard).isEqualTo(new ReadCardResponse(card.getCardId(), card.getCardTitle(), card.getBudget(), card.getDeadline(), card.getCardStatus()));
    }

    @DisplayName("카드 삭제 성공")
    @Test
    void deleteCard() {

        // given
        int cardId = 0;
        lenient().when(cardRepository.existsByCardId(cardId)).thenReturn(true);
        // when
        cardService.deleteCard(cardId);
        // then
        verify(cardRepository, times(1)).deleteByCardId(cardId);
    }

    @DisplayName("카드Ids 중 Backlog 상태인 카드 조회 성공")
    @Test
    void readCardsStausByIdsAndStatus_success() {
        // given
        List<Integer> cardIds = Arrays.asList(1, 2, 3);
        CardStatus cardStatus = CardStatus.BACKLOG;

        when(cardRepository.findByCardIdsAndCardStatus(cardIds, cardStatus))
            .thenReturn(Collections.singletonList(cards.get(0))); // 인수가 하나

        // when
        List<ReadCardResponse> cardResponses = cardService.readCardsStausByIdsAndStatus(cardIds, cardStatus);

        // then
        assertEquals(1, cardResponses.size());

        ReadCardResponse backlogCard = cardResponses.get(0);
        assertEquals(1, backlogCard.cardId());
        assertEquals("Card 1", backlogCard.cardTitle());
        assertEquals(1000, backlogCard.budget());
        assertEquals(LocalDate.of(2023, 6, 30), backlogCard.deadline());
        assertEquals(CardStatus.BACKLOG, backlogCard.cardStatus());
    }
}
