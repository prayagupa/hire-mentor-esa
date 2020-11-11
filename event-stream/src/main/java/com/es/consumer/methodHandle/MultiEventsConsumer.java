package com.es.consumer.methodHandle;

import com.es.consumer.Event;
import com.es.consumer.EventListener;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;

/**
 * Created by prayagupd
 * on 1/14/16.
 */

public class MultiEventsConsumer implements EventListener<Event> {

    Map<Class<? extends Event>, Optional<Method>> eventHandlers = new HashMap<>();

    @Override
    public void handleEvent(Event event) {
        MethodHandles.Lookup pointer = MethodHandles.lookup();

        try {
            Class listener = this.getClass();
            Optional<Method> eventHandler = eventHandlers.computeIfAbsent(event.getClass(),
                    eventClass -> getEventHandler(listener, event.getClass()));

            eventHandler.ifPresent(existingEventHandler -> {
                try {
                    MethodHandle eventHandle =
                            pointer.findVirtual(this.getClass(),
                                    existingEventHandler.getName(),
                                    MethodType.methodType(existingEventHandler.getReturnType(), event.getClass()));
                    eventHandle.invokeExact();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("xxx", e);
        }
    }

    private Optional<Method> getEventHandler(Class listener, Class<? extends Event> event) {
        return asList(listener.getMethods()).stream()
                .filter(method -> method.getParameterTypes().length > 0 &&
                        method.getParameterTypes()[0].isAssignableFrom(event))
                .findFirst();
    }
}
