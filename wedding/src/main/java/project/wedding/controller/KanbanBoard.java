package project.wedding.controller;

import project.wedding.domain.Card;
import project.wedding.domain.User;
import project.wedding.usecase.CardUseCase;

public class KanbanBoard {
    private final User user;
    private final CardContainer cardContainer;
    private final CardUseCase cardUseCase;

    public KanbanBoard(User user, CardContainer cardContainer, CardUseCase cardUseCase) {
        this.user = user;
        this.cardContainer = cardContainer;
        this.cardUseCase = cardUseCase;
    }

    //
    public void createCard(CardContainer cardContainer) {
        cardUseCase.addCard(cardContainer);
    }

    public void deleteCard(CardContainer cardContainer, int id) {
        cardUseCase.deleteCard(cardContainer, id);
    }

    public Card readCard(CardContainer cardContainer, int id) {
        return cardUseCase.readCard(cardContainer, id);
    }

    public void updateTodo(CardContainer cardContainer, int id, String title, String todo) {
        cardUseCase.updateTodo(cardContainer, id, title, todo);
    }
}
