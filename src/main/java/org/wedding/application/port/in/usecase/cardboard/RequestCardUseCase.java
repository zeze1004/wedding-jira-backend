package org.wedding.application.port.in.usecase.cardboard;

import org.wedding.application.port.in.command.card.ModifyCardCommand;

public interface RequestCardUseCase {

    void requestModifyCard(int cardId, ModifyCardCommand command);
}
