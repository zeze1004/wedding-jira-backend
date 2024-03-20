package org.wedding.adapter.in.web.dto;

import java.time.LocalDateTime;

import org.wedding.domain.CardStatus;
import org.wedding.domain.card.Card;
import org.wedding.domain.card.exception.CardError;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ModifyCardRequest (
    @NotBlank(message = "CARD_TITLE_IS_REQUIRED", payload = CardError.class)
    @Size(min = 1, max = 20, message = "CARD_TITLE_SIZE_NOT_VALID", payload = CardError.class)
    String cardTitle,
    Long budget,
    LocalDateTime deadline,
    CardStatus cardStatus
) {

    public static Card toEntity(Card card, String cardTitle) {
        return Card.builder()
            .cardId(card.getCardId())
            .cardTitle(cardTitle)
            .budget(card.getBudget())
            .deadline(card.getDeadline())
            .cardStatus(card.getCardStatus())
            .build();
    }

    public static Card toEntity(Card card, Long budget) {
        return Card.builder()
            .cardId(card.getCardId())
            .cardTitle(card.getCardTitle())
            .budget(budget)
            .deadline(card.getDeadline())
            .cardStatus(card.getCardStatus())
            .build();
    }

    public static Card toEntity(Card card, LocalDateTime deadline) {
        return Card.builder()
            .cardId(card.getCardId())
            .cardTitle(card.getCardTitle())
            .budget(card.getBudget())
            .deadline(deadline)
            .cardStatus(card.getCardStatus())
            .build();
    }

    public static Card toEntity(Card card, CardStatus cardStatus) {
        return Card.builder()
            .cardId(card.getCardId())
            .cardTitle(card.getCardTitle())
            .budget(card.getBudget())
            .deadline(card.getDeadline())
            .cardStatus(cardStatus)
            .build();
    }

    public String toString() {
        return """
            ModifyCardRequest{
                cardTitle='%s',
                budget='%s',
                deadline='%s',
                cardStatus='%s'
            }
            """.formatted(cardTitle, budget, deadline, cardStatus);
    }
}
