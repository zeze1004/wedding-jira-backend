package org.wedding.application.port.in.command.card;

public record DeleteCardCommand(

    int userId,
    int cardId
) {

    public String toString() {
        return """
            DeleteCardCommand{
                userId='%s',
                cardId='%s'
            }
            """.formatted(userId, cardId);
    }
}
