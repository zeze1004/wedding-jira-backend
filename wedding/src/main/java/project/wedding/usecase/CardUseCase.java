package project.wedding.usecase;

import project.wedding.domain.Card;
import project.wedding.controller.CardContainer;

public class CardUseCase implements AddCard, DeleteCard, ReadCard, UpdateCard {
    private final Card card;

    public CardUseCase(Card card) {
        this.card = card;
    }

    @Override
    public void addCard(CardContainer cardContainer) {
        cardContainer.save(card);
    }

    @Override
    public void deleteCard(CardContainer cardContainer, int id) {
        cardContainer.delete(id);
    }

    @Override
    public Card readCard(CardContainer cardContainer, int id) {
        return cardContainer.read(id);
    }

    @Override
    public void updateTodo(CardContainer cardContainer, int id, String title, String todo) {
        cardContainer.update(id, title, todo);
    }
}
