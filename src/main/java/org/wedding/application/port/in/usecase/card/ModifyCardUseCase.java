package org.wedding.application.port.in.usecase.card;

import org.wedding.application.port.in.command.card.ModifyCardCommand;

public interface ModifyCardUseCase {

    void modifyCard(int card, ModifyCardCommand command);
}
