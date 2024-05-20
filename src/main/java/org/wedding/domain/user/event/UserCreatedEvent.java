package org.wedding.domain.user.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class UserCreatedEvent extends ApplicationEvent {

    private final int userId;

    public UserCreatedEvent(int userId) {
        super(userId);
        this.userId = userId;
    }
}
