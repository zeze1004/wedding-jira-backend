package org.wedding.application.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.application.port.in.command.card.DeleteCardCommand;
import org.wedding.application.port.in.command.card.ModifyCardCommand;
import org.wedding.application.port.in.command.cardboard.CreateCardBoardCommand;
import org.wedding.application.port.in.command.cardboard.ReadCardCommand;
import org.wedding.application.port.in.usecase.card.DeleteCardUseCase;
import org.wedding.application.port.in.usecase.card.ModifyCardUseCase;
import org.wedding.application.port.in.usecase.card.ReadCardUseCase;
import org.wedding.application.port.in.usecase.cardboard.CardBoardUseCase;
import org.wedding.application.port.in.usecase.cardboard.CardOwnerShipValidator;
import org.wedding.application.port.in.usecase.cardboard.RequestCardUseCase;
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
public class CardBoardService implements CardBoardUseCase, CardOwnerShipValidator, RequestCardUseCase {

    private final CardBoardRepository cardBoardRepository;
    private final ReadCardUseCase readCardUseCase;
    private final ModifyCardUseCase modifyCardUseCase;
    private final DeleteCardUseCase deleteCardUseCase;

    @Override
    public void createCardBoard(CreateCardBoardCommand command) {
        CardBoard cardBoard = CreateCardBoardCommand.toEntity(command);
        cardBoardRepository.save(cardBoard);
    }

    @Transactional
    @Override
    public void addCardToCardBoard(int cardId, int userId) {
        CardBoard cardBoard = cardBoardRepository.findCardBoardByUserId(userId);
        isCardBoardNotFound(cardBoard);
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

    @Transactional
    @Override
    public void requestModifyCard(int cardId, ModifyCardCommand command) {
        if (!checkCardOwner(command.userId(), cardId)) {
            throw new CardBoardException(CardBoardError.CARD_OWNER_NOT_MATCH);
        }
        modifyCardUseCase.modifyCard(cardId, command);
    }

    @Transactional
    @Override
    public void requestDeleteCard(DeleteCardCommand command) {
        if (!checkCardOwner(command.userId(), command.cardId())) {
            throw new CardBoardException(CardBoardError.CARD_OWNER_NOT_MATCH);
        }
        deleteCardUseCase.deleteCard(command.cardId());
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkCardOwner(int userId, int cardId) {
        CardBoard cardBoard = cardBoardRepository.findCardBoardByUserId(userId);
        isCardBoardNotFound(cardBoard);
        return cardBoard.isCardOwner(userId, cardId);
    }

    private void isCardBoardNotFound(CardBoard cardBoard) {
        if (cardBoard == null) {
            throw new CardBoardException(CardBoardError.CARD_BOARD_NOT_FOUND);
        }
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
