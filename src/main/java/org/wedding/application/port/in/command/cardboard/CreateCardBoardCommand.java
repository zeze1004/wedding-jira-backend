package org.wedding.application.port.in.command.cardboard;

import java.util.List;

import org.wedding.domain.cardboard.CardBoard;

public record CreateCardBoardCommand(
    int userId,
    List<Integer> cardIds
) {

    public static CardBoard toEntity(CreateCardBoardCommand createCardBoardCommand) {
        return CardBoard.builder()
            .userId(createCardBoardCommand.userId())
            .cardIds(createCardBoardCommand.cardIds())
            .build();
    }

    public String toString() {
        return """
            CreateCardBoardCommand{
                userId='%s',
            }
            """.formatted(userId);
    }
}
