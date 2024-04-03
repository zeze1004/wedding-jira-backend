package org.wedding.common.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.stereotype.Component;

@Component
public class EventBusImpl implements EventBus {
    private final Map<Class<?>, List<Consumer<?>>> subscribers = new HashMap<>();

    @Override
    public <T> void publish(T event) {
        if (subscribers.containsKey(event.getClass())) {
            subscribers.get(event.getClass()).forEach(
                consumer -> ((Consumer<T>) consumer).accept(event));
        }
    }

    @Override
    public <T> void subscribe(T subscriber) {
        for (Method method : subscriber.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                Class<?> eventType = method.getParameterTypes()[0];
                subscribers.computeIfAbsent(eventType, k -> new ArrayList<>())
                    .add(event -> {
                        try {
                            method.invoke(subscriber, event);
                        } catch (Exception e) {
                            // TODO: 예외 처리 수정
                            throw new RuntimeException(e);
                        }
                    });
            }
        }
    }
}
