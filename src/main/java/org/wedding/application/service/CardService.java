package org.wedding.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.application.port.in.command.card.CreateCardCommand;
import org.wedding.application.port.in.command.card.ModifyCardCommand;
import org.wedding.application.port.in.usecase.card.CreateCardUseCase;
import org.wedding.application.port.in.usecase.card.ModifyCardUseCase;
import org.wedding.application.port.in.usecase.card.ReadCardUseCase;
import org.wedding.application.port.out.repository.CardRepository;
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

        cardRepository.save(modifiedCard);
    }

    @Override
    public Card readCardByCardId(int cardId) {
        return cardRepository.findByCardId(cardId);
    }

    @Override
    public Card readCardsByCardTitle(String cardTitle) {
        if (!cardRepository.existsByCardTitle(cardTitle)) {
            throw new CardException(CardError.CARD_NOT_FOUND);
        }

        return cardRepository.findByCardTitle(cardTitle);
    }

    @Override
    public List<Card> readCardsByCardStatus(CardStatus cardStatus) {
        return cardRepository.findByCardStatus(cardStatus);
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
