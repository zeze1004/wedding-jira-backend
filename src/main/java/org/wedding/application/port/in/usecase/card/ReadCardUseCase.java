package org.wedding.application.port.in.usecase.card;

import java.util.List;

import org.wedding.domain.CardStatus;
import org.wedding.domain.card.Card;

public interface ReadCardUseCase {

    Card readCardByCardId(int cardId);
    Card readCardsByCardTitle(String cardTitle);
    List<Card> readCardsByCardStatus(CardStatus cardStatus);

    // TODO: deadline에 따라 정렬 되는 기능은 캘린더 도메인 구현 후 추가하기
    // TODO: CardBoard 도메인 리팩토링 후 구현하기 (Card는 User를 몰라서 UserId로 찾을 수 없음)
    // Card readCardsByCardBoardId(int cardBoardId);
}
