package project.wedding.domain;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class CardBoard {
    LinkedHashMap<Integer, Card> cardList = new LinkedHashMap<>();

    public boolean createCard() {
        if (isSatisfied()) {
            Card card = new Card();
            cardList.put(card.getCardId(), card);
            return true;
        }
        return false;
    }

    private boolean isSatisfied() {
        return cardList.size() <= 20;
    }

    public String getAllValues() {
        StringBuilder result = new StringBuilder("[");
        for (Map.Entry<Integer, Card> entrySet : cardList.entrySet()) {
            result.append(Arrays.deepToString(
                new String[] {entrySet.getKey() + ": " + Arrays.deepToString(
                    entrySet.getValue().getTodos(entrySet.getKey()))})).append(", ");
        }
        if (result.length() > 1) {
            result.delete(result.length() - 2, result.length()); // 마지막 쉼표 제거
        }
        result.append("]");
        System.out.println(result.toString());
        return result.toString();
    }
}
