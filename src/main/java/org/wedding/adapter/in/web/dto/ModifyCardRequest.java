package org.wedding.adapter.in.web.dto;

import java.time.LocalDate;
import java.util.Optional;

import org.wedding.domain.CardStatus;

import lombok.Builder;

@Builder
public record ModifyCardRequest (

    Optional<String> cardTitle,
    Optional<Long> budget,
    Optional<LocalDate> deadline,
    Optional<CardStatus> cardStatus
) {

    public ModifyCardRequest(String cardTitle, Long budget, LocalDate deadline, CardStatus cardStatus) {
        this(Optional.ofNullable(cardTitle), Optional.ofNullable(budget), Optional.ofNullable(deadline), Optional.ofNullable(cardStatus));
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
