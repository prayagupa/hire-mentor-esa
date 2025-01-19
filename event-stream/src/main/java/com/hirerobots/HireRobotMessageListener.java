package com.hirerobots;

import com.es.consumer.MultiEventListener;
import com.hirerobots.domain.RobotHiredEvent;
import org.springframework.stereotype.Component;

/**
 * Created by prayagupd
 * on 11/16/15.
 */

@Component
public class HireRobotMessageListener extends MultiEventListener {

    public void handleEventType1(RobotHiredEvent event) {
        System.out.println("saving type1");
    }

    public void handleEventType2(TestEvent event) {
        System.out.println("saving type2");
    }

    class TestEvent {

    }
}
