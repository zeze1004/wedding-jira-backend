package org.wedding.common.event;

public interface EventBus {
    void publish(Object event);
    void subscribe(Object subscriber);
}
