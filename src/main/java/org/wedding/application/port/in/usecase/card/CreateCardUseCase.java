package org.wedding.application.port.in.usecase.card;

import org.wedding.application.port.in.command.card.CreateCardCommand;

public interface CreateCardUseCase {

    void createCard(CreateCardCommand command);
}
