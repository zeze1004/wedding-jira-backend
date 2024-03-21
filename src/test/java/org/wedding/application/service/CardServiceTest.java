package org.wedding.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.wedding.application.port.in.command.card.CreateCardCommand;
import org.wedding.application.port.in.command.card.ModifyCardCommand;
import org.wedding.application.port.out.repository.CardRepository;
import org.wedding.domain.CardStatus;
import org.wedding.domain.card.Card;
import org.wedding.domain.card.exception.CardError;
import org.wedding.domain.card.exception.CardException;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @InjectMocks
    private CardService cardService;
    @Mock
    private CardRepository cardRepository;

    private Card card;
    private CreateCardCommand createCommand;
    private ModifyCardCommand modifyCommand;

    @BeforeEach
    void setUp() {
        createCommand = new CreateCardCommand(
            "스드메 예약금 넣기",
            100000L,
            null
        );
        cardService = new CardService(cardRepository);
        card = CreateCardCommand.toEntity(createCommand);
        cardService.createCard(createCommand);
    }

    @DisplayName("카드 제목과 예산 그리고 마감일를 넣으면 카드 생성 성공")
    @Test
    void createCardWithAllValidPath() {

        CreateCardCommand allValidCreateCardCommand = new CreateCardCommand(
            "스드메 예약금 넣기",
            100000L,
            LocalDateTime.now()
        );
        when(cardRepository.existsByCardTitle(allValidCreateCardCommand.cardTitle())).thenReturn(false);
        cardService.createCard(createCommand);
        verify(cardRepository, times(2)).save(any());
    }

    @DisplayName("카드 제목만 넣고 예산, 마감일을 넣지 않아도 카드 생성 성공")
    @Test
    void createCardWithOnlyTitle() {

        CreateCardCommand createCardWithOnlyTitleCommand = new CreateCardCommand(
            "결혼식 리허설하기",
            null,
            null
        );
        when(cardRepository.existsByCardTitle(createCardWithOnlyTitleCommand.cardTitle())).thenReturn(false);
        cardService.createCard(createCardWithOnlyTitleCommand);
        verify(cardRepository, times(2)).save(any());
    }

    @DisplayName("카드 제목이 중복되면 카드 생성 실패")
    @Test
    void checkDuplicateCardTitle() {

        CreateCardCommand createCardDuplicateTitleRequest = new CreateCardCommand(
            "스드메 예약금 넣기",
            null,
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
            "스드메 예약금 넣기 수정",
            null,
            null,
            null
        );
        lenient().when(cardRepository.existsByCardId(card.getCardId())).thenReturn(true);
        lenient().when(cardRepository.existsByCardTitle(modifyCardTitleCommand.cardTitle().get())).thenReturn(false);
        lenient().when(cardRepository.findByCardId(card.getCardId())).thenReturn(card);
        cardService.modifyCard(card.getCardId(), modifyCardTitleCommand);
        verify(cardRepository, times(2)).save(any());
    }

    @DisplayName("카드가 존재하지 않을 때 카드 이름 수정할시 실패")
    @Test
    void modifyCard_Title_Fail() {

        ModifyCardCommand modifyCardTitleCommand = new ModifyCardCommand(
            "스드메 예약금 넣기 수정",
            null,
            null,
            null
        );
        try {
            cardService.checkCardExistence(-1); // 존재하지 않는 임의의 cardId
        } catch (CardException e) {
            assertEquals(CardError.CARD_NOT_FOUND, CardError.of("CARD_NOT_FOUND"));
        }
    }

    @DisplayName("카드 상태 변경")
    @Test
    void modifyCard_Status() {

        ModifyCardCommand modifyCardStatusCommand = new ModifyCardCommand(
            null,
            null,
            null,
            CardStatus.PROGRESS
        );

        lenient().when(cardRepository.existsByCardId(card.getCardId())).thenReturn(true);
        Assertions.assertThat(modifyCardStatusCommand.cardTitle()).isEqualTo(Optional.empty());
        lenient().when(cardRepository.findByCardId(card.getCardId())).thenReturn(card);
        cardService.modifyCard(card.getCardId(), modifyCardStatusCommand);
        verify(cardRepository, times(2)).save(any());
    }


    @DisplayName("카드 제목에 따른 카드 조회")
    @Test
    void readCardsByCardTitle() {

        // given
        String cardTitle = "스드메 예약금 넣기";
        lenient().when(cardRepository.existsByCardTitle(cardTitle)).thenReturn(true);
        lenient().when(cardRepository.findByCardTitle(cardTitle)).thenReturn(card);

        // when
        Card actualCard = cardService.readCardsByCardTitle(cardTitle);

        // then
        verify(cardRepository, times(1)).findByCardTitle(cardTitle);
        assertThat(actualCard).isEqualTo(card);
    }

    @DisplayName("카드 상태에 따른 카드 조회")
    @Test
    void readCardsByCardStatus() {

        // given
        List<Card> expectedCards = Arrays.asList(
            new Card(1, "스드메 예약금 넣기", 100000L, null, CardStatus.BACKLOG),
            new Card(2, "상견례하기", 100000L, null, CardStatus.BACKLOG));
        lenient().when(cardRepository.findByCardStatus(CardStatus.BACKLOG)).thenReturn(expectedCards);

        // when
        List<Card> actualCards = cardService.readCardsByCardStatus(CardStatus.BACKLOG);

        // then
        verify(cardRepository, times(1)).findByCardStatus(CardStatus.BACKLOG);
        assertThat(actualCards).isEqualTo(expectedCards);
    }
}
