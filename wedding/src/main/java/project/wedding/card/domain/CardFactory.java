package project.wedding.card.domain;

public class CardFactory {

    private static final CardFactory cardFactory = new CardFactory();

    public static Card getCardInstance() {
        return new Card();
    }

    private CardFactory() {}
}
