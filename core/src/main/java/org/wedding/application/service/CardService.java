package org.wedding.application.service;

import static org.springframework.transaction.annotation.Propagation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.application.port.in.command.card.CreateCardCommand;
import org.wedding.application.port.in.command.card.ModifyCardCommand;
import org.wedding.application.port.in.usecase.card.CreateCardUseCase;
import org.wedding.application.port.in.usecase.card.DeleteCardUseCase;
import org.wedding.application.port.in.usecase.card.ModifyCardUseCase;
import org.wedding.application.port.in.usecase.card.ReadCardUseCase;
import org.wedding.application.port.out.repository.CardRepository;
import org.wedding.application.service.response.card.ReadCardResponse;
import org.wedding.domain.CardStatus;
import org.wedding.domain.card.Card;
import org.wedding.domain.card.event.CardCreatedEvent;
import org.wedding.domain.card.exception.CardError;
import org.wedding.domain.card.exception.CardException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService implements CreateCardUseCase, ModifyCardUseCase, ReadCardUseCase, DeleteCardUseCase {

    private final CardRepository cardRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public void createCard(CreateCardCommand command) {

        checkDuplicateCardTitle(command.cardTitle());
        Card card = CreateCardCommand.toEntity(command);

        cardRepository.save(card);
        eventPublisher.publishEvent(new CardCreatedEvent(card.getCardId(), command.userId()));
    }

    @Transactional(propagation = MANDATORY)
    @Override
    public void modifyCard(int cardId, ModifyCardCommand command) {
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
        return new ReadCardResponse(card.getCardId(), card.getCardTitle(), card.getBudget(), card.getDeadline(),
            card.getCardStatus());
    }

    @Override
    public ReadCardResponse readCardsByCardTitle(String cardTitle) {

        checkCardTitleExistence(cardTitle);

        Card card = cardRepository.findByCardTitle(cardTitle);
        return new ReadCardResponse(card.getCardId(), card.getCardTitle(), card.getBudget(), card.getDeadline(),
            card.getCardStatus());
    }

    /* TODO: 카드보드 리팩토링 후 추가
    @Override
    public List<ReadCardResponse> readAllCardsByCardBoardId(int cardBoardId) {}
     */

    @Transactional(readOnly = true)
    @Override
    public List<ReadCardResponse> readCardsStausByIdsAndStatus(List<Integer> cardIds, CardStatus cardStatus) {
        List<Card> cards = cardRepository.findByCardIdsAndCardStatus(cardIds, cardStatus);

        return cards.stream()
            .map(this::toReadCardResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteCard(int cardId) {

        checkCardExistence(cardId);
        cardRepository.deleteByCardId(cardId);
    }

    private ReadCardResponse toReadCardResponse(Card card) {
        return new ReadCardResponse(card.getCardId(), card.getCardTitle(), card.getBudget(), card.getDeadline(),
            card.getCardStatus());
    }

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
