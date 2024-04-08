package org.wedding.application.port.in.usecase.card;

import java.util.List;

import org.wedding.application.service.response.card.ReadCardResponse;
import org.wedding.domain.CardStatus;

public interface ReadCardUseCase {

    ReadCardResponse readCardByCardId(int cardId);
    ReadCardResponse readCardsByCardTitle(String cardTitle);
    List<ReadCardResponse> readCardsStausByIdsAndStatus(List<Integer> cardIds, CardStatus cardStatus);

    // TODO: deadline에 따라 정렬 되는 기능은 캘린더 도메인 구현 후 추가하기
    // TODO: CardBoard 도메인 리팩토링 후 구현하기 (Card는 User를 몰라서 UserId로 찾을 수 없음)
    // List<ReadCardResponse> readAllCardsByCardBoardId(int cardBoardId);
}
