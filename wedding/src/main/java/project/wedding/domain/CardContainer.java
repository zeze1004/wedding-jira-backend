package project.wedding.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 생성된 카드를 저장하는 객체
 */
public class CardContainer implements CreatePolicy {
    private final List<Card> cards = new ArrayList<>();

    public void save(Card card) {
        if (isSatisfied()) {
            cards.add(card);
        }
    }

    @Override
    public boolean isSatisfied() {
        if (cards.size() >= 20) {
            throw new IllegalArgumentException("생성할 수 있는 카드 수를 초과했습니다.");
        }
        return true;
    }
}