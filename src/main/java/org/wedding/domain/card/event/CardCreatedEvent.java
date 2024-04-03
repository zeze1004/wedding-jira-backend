package org.wedding.domain.card.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class CardCreatedEvent extends ApplicationEvent {
    private final int cardId;
    private final int userId;

    public CardCreatedEvent(int userId, int cardId) {
        super(cardId);
        this.cardId = cardId;
        this.userId = userId;
    }
}
