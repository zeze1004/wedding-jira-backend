package org.wedding.application.port.in.command.card;

import java.time.LocalDateTime;

import org.wedding.domain.CardStatus;
import org.wedding.domain.card.Card;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateCardCommand (

    @NotNull
    String cardTitle,
    Long budget,
    LocalDateTime deadline
) {

    public CreateCardCommand(String cardTitle, Long budget, LocalDateTime deadline) {
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
