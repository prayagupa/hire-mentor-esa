package com.hirerobots;

import com.es.consumer.methodHandle.MultiEventsConsumer;
import com.hirerobots.domain.RobotHiredEvent;

/**
 * Created by prayagupd
 * on 1/15/16.
 */
public class TestMultiListener extends MultiEventsConsumer {

    public void handleEvent1(RobotHiredEvent event) {
        System.out.println("container");
    }


    public void handle() {
        System.out.println("container");
    }
}
