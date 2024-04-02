package org.wedding.common.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventBusImpl implements EventBus {

    private final Map<Class<?>, List<Consumer<Object>>> subscribers = new HashMap<>();

    public void publish(Object event) {
        if (subscribers.containsKey(event.getClass())) {
            subscribers.get(event.getClass()).forEach(consumer -> consumer.accept(event));
        }
    }

    public void subscribe(Object subscriber) {
        for (Method method : subscriber.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                Class<?> eventType = method.getParameterTypes()[0];
                subscribers.computeIfAbsent(eventType, k -> new ArrayList<>())
                    .add(event -> {
                        try {
                            method.invoke(subscriber, event);
                        } catch (Exception e) {
                            // 예외 처리
                            throw new RuntimeException(e);
                        }
                    });
            }
        }
    }
}
