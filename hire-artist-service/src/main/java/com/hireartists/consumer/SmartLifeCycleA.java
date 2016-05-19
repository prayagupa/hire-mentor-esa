package com.hireartists.consumer;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * Created by prayagupd
 * on 12/25/15.
 */

@Component
public class SmartLifeCycleA implements SmartLifecycle {
    @Override
    public boolean isAutoStartup() {
        return false;
    }

    @Override
    public void stop(Runnable callback) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return 0;
    }
}
