package org.wedding.application.port.in.command.cardboard;

import org.wedding.domain.CardStatus;

public record ReadCardCommand (
    int userId,
    CardStatus cardStatus
) {

    @Override
    public String toString() {
        return """
            ReadCardCommand{
                userId='%s',
                cardStatus='%s'
            }
            """.formatted(userId, cardStatus);
    }
}
