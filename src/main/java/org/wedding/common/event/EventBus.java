package org.wedding.common.event;

public interface EventBus {
    <T> void publish(T event);
    <T> void subscribe(T subscriber);
}
