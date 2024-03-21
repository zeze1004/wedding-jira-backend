package org.wedding.domain.card;

import java.time.LocalDateTime;
import java.util.List;

import org.wedding.domain.CardStatus;
import org.wedding.domain.Todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Card {
    private final int cardId;
    private final String cardTitle;
    private final Long budget;
    private final LocalDateTime deadline;   // TODO: Calendar 도메인으로 변경
    private final List<Todo> todoList;
    private final CardStatus cardStatus;

    public Card(int cardId, String cardTitle, long budget, LocalDateTime deadline, CardStatus cardStatus) {
        this.cardId = cardId;
        this.cardTitle = cardTitle;
        this.budget = budget;
        this.deadline = deadline;
        this.todoList = null;
        this.cardStatus = cardStatus;
    }

    public Card changeCardTitle(String cardTitle) {
        return new Card(this.cardId, cardTitle, this.budget, this.deadline, this.todoList, this.cardStatus);
    }

    public Card changeBudget(Long budget) {
        return new Card(this.cardId, this.cardTitle, budget, this.deadline, this.todoList, this.cardStatus);

    }

    public Card changeDeadline(LocalDateTime deadline) {
        return new Card(this.cardId, this.cardTitle, this.budget, deadline, this.todoList, this.cardStatus);
    }

    public Card changeCardStatus(CardStatus cardStatus) {
        return new Card(this.cardId, this.cardTitle, this.budget, this.deadline, this.todoList, cardStatus);
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
