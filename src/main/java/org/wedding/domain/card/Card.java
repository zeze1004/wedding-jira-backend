package org.wedding.domain.card;

import java.time.LocalDateTime;
import java.util.List;

import org.wedding.domain.CardStatus;
import org.wedding.domain.Todo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Card {
    private final int cardId;
    private final String cardTitle;
    private final Long budget;
    private final LocalDateTime deadline;   // TODO: Calendar 도메인으로 변경
    private final List<Todo> todoList;
    private final CardStatus cardStatus;

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
