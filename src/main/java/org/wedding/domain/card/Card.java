package org.wedding.domain.card;

import java.time.LocalDate;

import org.wedding.domain.CardStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Card {
    private final int cardId;
    private final String cardTitle;
    private final long budget;
    private final LocalDate deadline;   // TODO: Calendar 도메인으로 변경
    private final CardStatus cardStatus;

    public Card changeCardTitle(String cardTitle) {
        return new Card(this.cardId, cardTitle, this.budget, this.deadline, this.cardStatus);
    }

    public Card changeBudget(Long budget) {
        return new Card(this.cardId, this.cardTitle, budget, this.deadline, this.cardStatus);

    }

    public Card changeDeadline(LocalDate deadline) {
        return new Card(this.cardId, this.cardTitle, this.budget, deadline, this.cardStatus);
    }

    public Card changeCardStatus(CardStatus cardStatus) {
        return new Card(this.cardId, this.cardTitle, this.budget, this.deadline, cardStatus);
    }

    public String toString() {
        return """
            Card{
                CardId=%d,
                title='%s',
                cardStatus=%s
            }
            """.formatted(cardId, cardTitle, cardStatus);
    }
}
