package org.wedding.application.port.out.repository;

import org.wedding.domain.cardboard.CardBoard;

public interface CardBoardRepository {

    int save(CardBoard cardBoard);
    void addCardIds(int cardBoardId, int cardId);
    CardBoard findCardBoardByUserId(int userId);
}
