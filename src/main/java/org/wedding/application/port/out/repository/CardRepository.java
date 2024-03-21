package org.wedding.application.port.out.repository;

import java.util.List;

import org.wedding.domain.CardStatus;
import org.wedding.domain.card.Card;

public interface CardRepository {
    int save(Card card);

    boolean existsByCardId(int cardId);

    boolean existsByCardTitle(String cardTitle);

    Card findByCardId(int cardId);

    Card findByCardTitle(String cardTitle);

    List<Card> findByCardStatus(CardStatus cardStatus);
}
