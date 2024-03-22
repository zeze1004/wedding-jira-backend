package org.wedding.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.application.port.in.command.card.CreateCardCommand;
import org.wedding.application.port.in.command.card.ModifyCardCommand;
import org.wedding.application.port.in.usecase.card.CreateCardUseCase;
import org.wedding.application.port.in.usecase.card.ModifyCardUseCase;
import org.wedding.application.port.in.usecase.card.ReadCardUseCase;
import org.wedding.application.port.out.repository.CardRepository;
import org.wedding.application.service.response.card.ReadCardResponse;
import org.wedding.domain.CardStatus;
import org.wedding.domain.card.Card;
import org.wedding.domain.card.exception.CardError;
import org.wedding.domain.card.exception.CardException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService implements CreateCardUseCase, ModifyCardUseCase, ReadCardUseCase {

    private final CardRepository cardRepository;

    @Transactional
    @Override
    public void createCard(CreateCardCommand command) {

        checkDuplicateCardTitle(command.cardTitle());
        Card card = CreateCardCommand.toEntity(command);

        cardRepository.save(card);
    }

    @Transactional
    @Override
    public void modifyCard(int cardId, ModifyCardCommand command) {

        checkCardExistence(cardId);
        Card card = cardRepository.findByCardId(cardId);

        if (command.cardTitle().isPresent()) {
            checkDuplicateCardTitle(command.cardTitle().get());
        }

        Card modifiedCard =
            card.changeCardTitle(command.cardTitle().orElse(card.getCardTitle()))
                .changeBudget(command.budget().orElse(card.getBudget()))
                .changeDeadline(command.deadline().orElse(card.getDeadline()))
                .changeCardStatus(command.cardStatus().orElse(card.getCardStatus()));

        cardRepository.update(modifiedCard);
    }

    @Override
    public ReadCardResponse readCardByCardId(int cardId) {

        checkCardExistence(cardId);

        Card card = cardRepository.findByCardId(cardId);
        return new ReadCardResponse(card.getCardId(), card.getCardTitle(), card.getBudget(), card.getDeadline(), card.getCardStatus());
    }

    @Override
    public ReadCardResponse readCardsByCardTitle(String cardTitle) {

        checkCardTitleExistence(cardTitle);

        Card card = cardRepository.findByCardTitle(cardTitle);
        return new ReadCardResponse(card.getCardId(), card.getCardTitle(), card.getBudget(), card.getDeadline(), card.getCardStatus());
    }

    @Override
    public List<ReadCardResponse> readCardsByCardStatus(CardStatus cardStatus) {

        List<Card> cards = cardRepository.findByCardStatus(cardStatus);

        if (cards.isEmpty()) {
            throw new CardException(CardError.CARD_NOT_FOUND);
        }

        return cards.stream()
            .map(card -> new ReadCardResponse(card.getCardId(), card.getCardTitle(), card.getBudget(), card.getDeadline(), card.getCardStatus()))
            .collect(Collectors.toList());
    }

    /* TODO: 카드보드 리팩토링 후 추가
    @Override
    public List<ReadCardResponse> readAllCardsByCardBoardId(int cardBoardId) {}
     */

    public void checkCardTitleExistence(String cardTitle) {
        if (!cardRepository.existsByCardTitle(cardTitle)) {
            throw new CardException(CardError.CARD_NOT_FOUND);
        }
    }

    public void checkCardExistence(int cardId) {
        if (!cardRepository.existsByCardId(cardId)) {
            throw new CardException(CardError.CARD_NOT_FOUND);
        }
    }

    public void checkDuplicateCardTitle(String cardTitle) {
        if (cardRepository.existsByCardTitle(cardTitle)) {
            throw new CardException(CardError.CARD_TITLE_IS_DUPLICATED);
        }
    }
}
