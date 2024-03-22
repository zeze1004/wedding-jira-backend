package org.wedding.application.port.out.repository;

import java.util.List;

import org.wedding.domain.CardStatus;
import org.wedding.domain.card.Card;

public interface CardRepository {
    int save(Card card);

    void update(Card card);

    boolean existsByCardId(int cardId);

    boolean existsByCardTitle(String cardTitle);

    Card findByCardId(int cardId);

    Card findByCardTitle(String cardTitle);

    List<Card> findByCardStatus(CardStatus cardStatus);

    // List<Card> findAll(int cardBoardId); // TODO: 카드보드 리팩토링 후 추가

    // List<TodoListPort> findTodoByCardId(int cardId); // TODO: 투두 도메인 리팩토링 후 추가
}
