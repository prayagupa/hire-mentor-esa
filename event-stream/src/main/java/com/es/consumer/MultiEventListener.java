package com.es.consumer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;

/**
 * Created by prayagupd
 * on 1/14/16.
 */

public class MultiEventListener implements EventListener<Event> {

    Map<Class<? extends Event>, Optional<Method>> eventHandlers = new HashMap<>();

    @Override
    public void handleEvent(Event event) {
        Class listener = this.getClass();
        Optional<Method> eventHandler = eventHandlers.computeIfAbsent(event.getClass(),
                                                eventClass -> getEventHandler(listener, event.getClass()));

        eventHandler.ifPresent(eventHandler_ -> {
            try {
                eventHandler_.invoke(this, event);
            } catch (Exception e) {
                throw new RuntimeException("Can't handle event.", e);
            }
        });
    }

    private Optional<Method> getEventHandler(Class listener, Class<? extends Event> event) {
        return asList(listener.getMethods()).stream()
                .filter(method -> method.getParameterTypes().length > 0 &&
                        method.getParameterTypes()[0].isAssignableFrom(event))
                .findFirst();
    }


    private void initializeMap(Class listener, Class<? extends Event> event) {
        asList(listener.getMethods()).stream()
                .filter(method -> method.getParameterTypes()[0].isAssignableFrom(event.getClass()))
                .map(method1 -> eventHandlers.put(event, Optional.of(method1)));
    }
}
