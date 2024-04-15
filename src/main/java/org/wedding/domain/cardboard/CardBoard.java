package org.wedding.domain.cardboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class CardBoard {
    private final int cardBoardId;
    private final int userId;
    private final List<Integer> cardIds;

    public CardBoard(int cardBoardId, int userId, List<Integer> cardIds) {
        this.cardBoardId = cardBoardId;
        this.userId = userId;
        this.cardIds = cardIds != null ? cardIds : Collections.emptyList();
    }

    public List<Integer> getCardIds() {
        return Collections.unmodifiableList(cardIds);
    }

    public void addCard(int cardId) {
        synchronized (cardIds) {
            cardIds.add(cardId);
        }
    }

    public boolean isCardOwner(int userId, int cardId) {
        if (this.userId != userId) {
            return false;
        }

        return cardIds.contains(cardId);
    }

    public List<Integer> getAllCardId() {
        synchronized (cardIds) {
            return new ArrayList<>(cardIds);
        }
    }

    public void removeCard(int cardId) {
        synchronized (cardIds) {
            cardIds.remove(cardId);
        }
    }
}
