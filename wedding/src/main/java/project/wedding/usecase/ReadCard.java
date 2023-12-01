package project.wedding.usecase;

import project.wedding.domain.Card;
import project.wedding.controller.CardContainer;

public interface ReadCard {
    Card readCard(CardContainer cardContainer, int id);
}
