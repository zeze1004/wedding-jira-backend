package org.wedding.application.port.in;

import org.wedding.application.port.in.command.cardboard.CreateCardBoardCommand;

public interface CardBoardUseCase {
    void createCardBoard(CreateCardBoardCommand command);
    void addCardToCardBoard(int cardId, int userId);
}
