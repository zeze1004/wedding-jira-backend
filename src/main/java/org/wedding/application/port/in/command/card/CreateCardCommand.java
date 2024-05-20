package org.wedding.application.port.in.command.card;

import java.time.LocalDate;

import org.wedding.domain.CardStatus;
import org.wedding.domain.card.Card;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateCardCommand(

    int userId,
    @NotNull
    String cardTitle,
    Long budget,
    LocalDate deadline
) {

    public CreateCardCommand(int userId, String cardTitle, Long budget, LocalDate deadline) {
        this.userId = userId;
        this.cardTitle = cardTitle;
        this.budget = budget;
        this.deadline = deadline;
    }

    public static Card toEntity(CreateCardCommand createCardCommand) {
        return Card.builder()
            .cardTitle(createCardCommand.cardTitle())
            .budget(createCardCommand.budget())
            .deadline(createCardCommand.deadline())
            .cardStatus(CardStatus.BACKLOG)
            .build();
    }

    public String toString() {
        return """
            CreateCardCommand{
                cardTitle='%s',
                budget='%s',
                deadline='%s'
            }
            """.formatted(cardTitle, budget, deadline);
    }
}
