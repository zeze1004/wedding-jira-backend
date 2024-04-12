package org.wedding.application.service.response.cardboard;

import java.time.LocalDate;

import org.wedding.domain.CardStatus;

public record CardInfo(
    int cardId,
    String cardTitle,
    Long budget,
    LocalDate deadline,
    CardStatus cardStatus
) {
}
