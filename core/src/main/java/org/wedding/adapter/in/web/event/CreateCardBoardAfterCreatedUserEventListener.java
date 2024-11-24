package org.wedding.adapter.in.web.event;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.wedding.application.port.in.command.cardboard.CreateCardBoardCommand;
import org.wedding.application.port.in.usecase.cardboard.CardBoardUseCase;
import org.wedding.domain.user.event.CreateCardBoardAfterCreatedUserEvent;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CreateCardBoardAfterCreatedUserEventListener {

    private final CardBoardUseCase cardBoardUseCase;

    @EventListener
    public void handleUserCreatedEvent(CreateCardBoardAfterCreatedUserEvent event) {
        CreateCardBoardCommand command = new CreateCardBoardCommand(event.getUserId(), List.of());
        cardBoardUseCase.createCardBoard(command);
    }
}
