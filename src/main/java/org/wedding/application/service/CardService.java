package org.wedding.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wedding.adapter.in.web.dto.CreateCardRequest;
import org.wedding.application.port.in.usecase.CreateCardUseCase;
import org.wedding.application.port.out.repository.CardRepository;
import org.wedding.domain.card.Card;
import org.wedding.domain.card.exception.CardError;
import org.wedding.domain.card.exception.CardException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService implements CreateCardUseCase {

    private final CardRepository cardRepository;

    @Transactional
    @Override
    public void createCard(CreateCardRequest request) {
        checkDuplicateCardTitle(request.cardTitle());
        Card card = CreateCardRequest.toEntity(request);

        cardRepository.save(card);

    }

    public void checkDuplicateCardTitle(String cardTitle) {
        if (cardRepository.existsByCardTitle(cardTitle)) {
            throw new CardException(CardError.CARD_TITLE_IS_DUPLICATED);
        }
    }
}
