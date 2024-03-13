package org.wedding.domain.card;

public class CardFactory {

    private static final CardFactory cardFactory = new CardFactory();

    public static Card getCardInstance() {
        return new Card();
    }

    private CardFactory() {}
}
