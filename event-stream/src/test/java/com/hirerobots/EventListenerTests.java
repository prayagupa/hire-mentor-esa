package com.hirerobots;

import com.hirerobots.domain.RobotHiredEvent;
import org.junit.Test;

/**
 * Created by prayagupd
 * on 1/14/16.
 */

public class EventListenerTests {

    HireRobotMessageListener hireRobotMessageListener;

    @Test
    public void onHandleShouldDispatchEvent() {
        hireRobotMessageListener = new HireRobotMessageListener();
        hireRobotMessageListener.handleEvent(new RobotHiredEvent());
    }

    @Test
    public void onHandle() {
        TestMultiListener testMultiListener = new TestMultiListener();
        testMultiListener.handleEvent1(new RobotHiredEvent());
    }
}
