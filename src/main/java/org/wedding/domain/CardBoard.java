package org.wedding.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

@Getter
public class CardBoard {

    private final int cardBoardId;
    private final int userId;
    private final List<Integer> cardIdList;

    public CardBoard(int cardBoardId, int userId) {
        this.cardBoardId = cardBoardId;
        this.userId = userId;
        this.cardIdList = Collections.synchronizedList(new ArrayList<>());
    }

    public List<Integer> getCardIdList() {
        return Collections.unmodifiableList(cardIdList);
    }

    public void addCard(int cardId) {
        synchronized (cardIdList) {
            cardIdList.add(cardId);
        }
    }

    public List<Integer> getAllCardId() {
        synchronized (cardIdList) {
            return new ArrayList<>(cardIdList);
        }
    }

    public void removeCard(int cardId) {
        synchronized (cardIdList) {
            cardIdList.remove(cardId);
        }
    }
}
