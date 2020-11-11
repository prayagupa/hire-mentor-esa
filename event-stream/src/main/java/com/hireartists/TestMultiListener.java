package com.hireartists;

import com.es.consumer.methodHandle.MultiEventsConsumer;
import com.hireartists.domain.ArtistHiredEvent;

/**
 * Created by prayagupd
 * on 1/15/16.
 */
public class TestMultiListener extends MultiEventsConsumer {

    public void handleEvent1(ArtistHiredEvent event) {
        System.out.println("container");
    }


    public void handle() {
        System.out.println("container");
    }
}
