package project.wedding.domain;

/**
 * CardContainer에 card를 전달하여 투두 카드 생성하는 객체
 */
public class CreateCard {
    private final Card card;

    public CreateCard(Card card) {
        this.card = card;
    }

    public void addCard(CardContainer cardContainer) {
        cardContainer.save(card);
    }
}
