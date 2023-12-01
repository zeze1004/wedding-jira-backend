package project.wedding.controller;

import java.util.ArrayList;
import java.util.List;

import project.wedding.domain.Card;
import project.wedding.policy.CreatePolicy;

/**
 * 생성된 카드를 저장하는 객체
 */
public class CardContainer implements CreatePolicy {
    private final List<Card> cards = new ArrayList<>();

    public void save(Card card) {
        int id = cards.size();
        card.setId(id++);
        if (isSatisfied()) {
            cards.add(card);
        }
    }

    public void delete(int id) {
        // 예외처리 추가 필요
        cards.remove(id);
    }

    public Card read(int id) {
        // 무엇을 주는지 명확히 정의 필요
        return cards.get(id);
    }

    public void update(int id, String title, String todo) {
        // set(id, Card)
    }

    @Override
    public boolean isSatisfied() {
        if (cards.size() >= 21) {
            throw new IllegalArgumentException("생성할 수 있는 카드 수를 초과했습니다.");
        }
        return true;
    }

    @Override
    public String toString() {
        return "cards.size = " + cards.size();
    }
}
