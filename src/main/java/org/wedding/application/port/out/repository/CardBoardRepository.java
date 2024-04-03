package org.wedding.application.port.out.repository;

import org.wedding.domain.CardBoard;

public interface CardBoardRepository {

    CardBoard save(CardBoard cardBoard);
    CardBoard findByUserId(int userId);
}
