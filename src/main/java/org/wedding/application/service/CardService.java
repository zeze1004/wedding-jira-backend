package org.wedding.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.adapter.in.web.dto.CreateCardRequest;
import org.wedding.adapter.in.web.dto.ModifyCardRequest;
import org.wedding.application.port.in.usecase.CreateCardUseCase;
import org.wedding.application.port.in.usecase.ModifyCardUseCase;
import org.wedding.application.port.out.repository.CardRepository;
import org.wedding.domain.card.Card;
import org.wedding.domain.card.exception.CardError;
import org.wedding.domain.card.exception.CardException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService implements CreateCardUseCase, ModifyCardUseCase {

    private final CardRepository cardRepository;

    @Transactional
    @Override
    public void createCard(CreateCardRequest request) {
        checkDuplicateCardTitle(request.cardTitle());
        Card card = CreateCardRequest.toEntity(request);

        cardRepository.save(card);
    }


    @Transactional
    @Override
    public void modifyCard(int cardId, ModifyCardRequest request) {

        checkCardExistence(cardId);
        Card card = cardRepository.findByCardId(cardId);

        if (request.cardTitle().isPresent()) {
            checkDuplicateCardTitle(request.cardTitle().get());
        }

        Card modifiedCard =
            card.changeCardTitle(request.cardTitle().orElse(card.getCardTitle()))
                .changeBudget(request.budget().orElse(card.getBudget()))
                .changeDeadline(request.deadline().orElse(card.getDeadline()))
                .changeCardStatus(request.cardStatus().orElse(card.getCardStatus()));

        cardRepository.save(modifiedCard);
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
