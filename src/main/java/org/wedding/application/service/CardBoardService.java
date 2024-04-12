package org.wedding.application.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.application.port.in.CardBoardUseCase;
import org.wedding.application.port.in.command.cardboard.CreateCardBoardCommand;
import org.wedding.application.port.in.command.cardboard.ReadCardCommand;
import org.wedding.application.port.in.usecase.card.ReadCardUseCase;
import org.wedding.application.port.out.repository.CardBoardRepository;
import org.wedding.application.service.response.card.ReadCardResponse;
import org.wedding.application.service.response.cardboard.CardInfo;
import org.wedding.domain.cardboard.CardBoard;
import org.wedding.domain.cardboard.exception.CardBoardError;
import org.wedding.domain.cardboard.exception.CardBoardException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CardBoardService implements CardBoardUseCase {

    private final CardBoardRepository cardBoardRepository;
    private final ReadCardUseCase readCardUseCase;

    @Override
    public void createCardBoard(CreateCardBoardCommand command) {
        CardBoard cardBoard = CreateCardBoardCommand.toEntity(command);
        cardBoardRepository.save(cardBoard);
    }

    @Transactional
    @Override
    public void addCardToCardBoard(int cardId, int userId) {
        CardBoard cardBoard = cardBoardRepository.findCardBoardByUserId(userId);
        if (cardBoard == null) {
            throw new CardBoardException(CardBoardError.CARD_BOARD_NOT_FOUND);
        }
        cardBoard.addCard(cardId);
        cardBoardRepository.addCardIds(cardBoard.getCardBoardId(), cardId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CardInfo> readCardsByStatus(ReadCardCommand command) {
        String cardIdsStr = cardBoardRepository
            .findCardIdsByUserId(command.userId())
            .toString().replaceFirst("\\[", "").replaceFirst("]", "");

        List<Integer> cardIds = convertStringToList(cardIdsStr);

        List<ReadCardResponse> cardResponses =
            readCardUseCase.readCardsStausByIdsAndStatus(cardIds, command.cardStatus());
        return toCardBoardCardInfo(cardResponses);
    }

    private List<CardInfo> toCardBoardCardInfo(List<ReadCardResponse> cardResponses) {
        List<CardInfo> cardInfos = new ArrayList<>();
        for (ReadCardResponse cardResponse : cardResponses) {
            cardInfos.add(
                new CardInfo(
                    cardResponse.cardId(),
                    cardResponse.cardTitle(),
                    cardResponse.budget(),
                    cardResponse.deadline(),
                    cardResponse.cardStatus()
                ));
        }
        return cardInfos;
    }

    public List<Integer> convertStringToList(String numberListStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(numberListStr, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("CardIds로 리스트 변환이 실패했습니다.", e);
        }
    }
}
