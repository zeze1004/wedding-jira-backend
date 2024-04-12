package org.wedding.adapter.out.dto;

import java.time.LocalDate;

import org.wedding.domain.CardStatus;

public record CardsResponse(
    int cardId,
    String cardTitle,
    Long budget,
    LocalDate deadline,
    CardStatus cardStatus
) {
}
