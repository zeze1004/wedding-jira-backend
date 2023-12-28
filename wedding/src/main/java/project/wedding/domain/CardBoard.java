package project.wedding.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CardBoard {
    private LinkedHashMap<Integer, Card> cardList = new LinkedHashMap<>();

    public boolean createCard() {
        if (isSatisfied()) {
            Card card = CardFactory.getCardInstance();
            cardList.put(card.getCardId(), card);
            return true;
        }
        return false;
    }

    private boolean isSatisfied() {
        return cardList.size() <= 20;
    }

    public ArrayList getAllTodoCards() {
        ArrayList<Card> todoCards = new ArrayList<>(cardList.values());
        for (Map.Entry<Integer, Card> integerCardEntry : cardList.entrySet()) {
            System.out.println(integerCardEntry.getKey() + " " + integerCardEntry.getValue());
        }
        return todoCards;
    }

    public ArrayList getAllProgressCards() {
        List<Card> progressCards = new ArrayList<>(cardList.values());
        return progressCards.stream()
            .filter(card -> card.getCardStatus().equals(CardStatus.PROGRESS)).collect(Collectors.toCollection(ArrayList::new));
    }
}
