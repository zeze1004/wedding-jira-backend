package org.wedding.application.service;

import org.springframework.stereotype.Service;
import org.wedding.application.port.in.CardBoardUseCase;
import org.wedding.application.port.in.command.cardboard.CreateCardBoardCommand;
import org.wedding.application.port.out.repository.CardBoardRepository;
import org.wedding.domain.cardboard.CardBoard;
import org.wedding.domain.cardboard.exception.CardBoardError;
import org.wedding.domain.cardboard.exception.CardBoardException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CardBoardService implements CardBoardUseCase {

    private final CardBoardRepository cardBoardRepository;

    @Override
    public void createCardBoard(CreateCardBoardCommand command) {
        CardBoard cardBoard = CreateCardBoardCommand.toEntity(command);
        cardBoardRepository.save(cardBoard);
    }

    @Override
    public void addCardToCardBoard(int cardId, int userId) {
        CardBoard cardBoard = cardBoardRepository.findCardBoardByUserId(userId);
        if (cardBoard == null) {
            throw new CardBoardException(CardBoardError.CARD_BOARD_NOT_FOUND);
        }
        cardBoard.addCard(cardId);
        cardBoardRepository.addCardIds(cardBoard.getCardBoardId(), cardId);
    }
}
