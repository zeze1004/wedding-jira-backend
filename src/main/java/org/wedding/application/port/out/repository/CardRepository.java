package org.wedding.application.port.out.repository;

import org.wedding.domain.card.Card;

public interface CardRepository {
    int save(Card card);

    void deleteById(int cardId);

    void updateCardTitle(Card card, String cardTitle);

    boolean existsByCardId(int cardId);

    boolean existsByCardTitle(String cardTitle);
}
