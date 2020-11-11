package com.es.producer;

/**
 * Created by prayagupd
 * on 12/8/15.
 */

public interface Producer<R extends String> {
    <R extends String> String sendEvent();
}
