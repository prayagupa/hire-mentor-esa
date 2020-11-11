package com.es.consumer;

/**
 * Created by prayagupd
 * on 1/14/16.
 */

public abstract class Event {
    public String getEventType() {
        return this.getClass().getSimpleName();
    }
}
