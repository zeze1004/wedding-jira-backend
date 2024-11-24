package org.wedding.domain.user.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class CreateCardBoardAfterCreatedUserEvent extends ApplicationEvent {

    private final int userId;

    public CreateCardBoardAfterCreatedUserEvent(int userId) {
        super(userId);
        this.userId = userId;
    }
}
