package project.wedding.usecase;

import project.wedding.controller.CardContainer;

public interface UpdateCard {
    void updateTodo(CardContainer cardContainer, int id, String title, String todo);
}
