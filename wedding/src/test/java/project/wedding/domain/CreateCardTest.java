package project.wedding.domain;

import org.junit.jupiter.api.Test;

class CreateCardTest {

    @Test
    void addCard() {
        Card card = new Card();
        CreateCard createCard = new CreateCard(card);
        CardContainer cardContainer = new CardContainer();

        createCard.addCard(cardContainer);
    }
}
