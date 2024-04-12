package org.wedding.application.port.in;

import java.util.List;

import org.wedding.application.port.in.command.cardboard.CreateCardBoardCommand;
import org.wedding.application.port.in.command.cardboard.ReadCardCommand;
import org.wedding.application.service.response.cardboard.CardInfo;

public interface CardBoardUseCase {
    void createCardBoard(CreateCardBoardCommand command);
    void addCardToCardBoard(int cardId, int userId);
    List<CardInfo> readCardsByStatus(ReadCardCommand command);
}
