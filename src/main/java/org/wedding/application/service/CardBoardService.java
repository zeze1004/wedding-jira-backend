package org.wedding.application.service;

import static java.util.Objects.*;

import org.springframework.stereotype.Service;
import org.wedding.application.port.in.CardBoardUseCase;
import org.wedding.application.port.out.repository.CardBoardRepository;
import org.wedding.domain.CardBoard;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CardBoardService implements CardBoardUseCase {

    private final CardBoardRepository cardBoardRepository;

    @Override
    public void addCardToCardBoard(int cardId, int userId) {
        CardBoard cardBoard = cardBoardRepository.findByUserId(userId);
        if (cardBoard == null) {
            // TODO: cardBoard 예외 처리 추가
            throw new RuntimeException("CardBoard not found for userId: " + userId);
        }
        requireNonNull(cardBoard).addCard(cardId);
        cardBoardRepository.save(cardBoard);
    }
}
