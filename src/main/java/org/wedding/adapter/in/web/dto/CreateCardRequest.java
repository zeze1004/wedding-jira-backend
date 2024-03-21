package org.wedding.adapter.in.web.dto;

import java.time.LocalDateTime;

import org.wedding.domain.card.exception.CardError;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateCardRequest (

    @NotBlank(message = "CARD_TITLE_IS_REQUIRED", payload = CardError.class)
    @Size(min = 1, max = 20, message = "CARD_TITLE_SIZE_NOT_VALID", payload = CardError.class)
    String cardTitle,
    Long budget,
    LocalDateTime deadline
) {
    public CreateCardRequest(String cardTitle, Long budget, LocalDateTime deadline) {
        this.cardTitle = cardTitle;
        this.budget = budget;
        this.deadline = deadline;
    }

    public CreateCardRequest(String cardTitle) {
        this(cardTitle, null, null);
    }

    public CreateCardRequest(String cardTitle, Long budget) {
        this(cardTitle, budget, null);
    }

    public CreateCardRequest(String cardTitle, LocalDateTime deadline) {
        this(cardTitle, null, deadline);
    }

    public String toString() {
        return """
            CreateCardRequest{
                cardTitle='%s',
                budget='%s',
                deadline='%s'
            }
            """.formatted(cardTitle, budget, deadline);
    }
}
