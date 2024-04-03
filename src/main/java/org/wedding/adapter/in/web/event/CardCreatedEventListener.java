package org.wedding.adapter.in.web.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.wedding.application.port.in.CardBoardUseCase;
import org.wedding.domain.card.event.CardCreatedEvent;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CardCreatedEventListener {

    private final CardBoardUseCase cardBoardService;

    @EventListener
    public void handleCardCreatedEvent(CardCreatedEvent event) {
        int cardId = event.getCardId();
        int userId = event.getUserId();
        cardBoardService.addCardToCardBoard(cardId, userId);
    }
}
