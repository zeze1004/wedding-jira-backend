package org.wedding.application.service.response.card;

import java.time.LocalDate;

import org.wedding.domain.CardStatus;

public record ReadCardResponse(

    int cardId,
    String cardTitle,
    Long budget,
    LocalDate deadline,
    CardStatus cardStatus
) {
}
