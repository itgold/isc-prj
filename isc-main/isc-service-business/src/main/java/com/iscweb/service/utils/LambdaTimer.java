package com.iscweb.service.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple timer that support lambdas as a parameter.
 *
 * @author skurenkov
 */
@Slf4j
public class LambdaTimer {

    private final Timer timer = new Timer();
    private final Runnable runnable;

    private LambdaTimer(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * Timer factory and initialization method.
     *
     * @param runnable runnable to be executed by the timer.
     * @return a reference to the timer.
     */
    public static LambdaTimer init(Runnable runnable) {
        log.debug("Timer initialized for Runnable {}", runnable);
        return new LambdaTimer(runnable);
    }

    /**
     * Schedules runnable execution in a specified number of seconds.
     *
     * @param delaySec delay in seconds before timer execution.
     * @return timer task reference.
     */
    public TimerTask schedule(int delaySec) {
        final TimerTask task = new TimerTask() {

            @Override
            public void run() {
                log.debug("Executing timer {}", runnable);
                runnable.run();
            }
        };
        log.debug("Timer {} will be executed in {} sec", runnable, delaySec);
        timer.schedule(task, delaySec * 1000);

        return task;
    }

    public void cancel() {
        timer.cancel();
    }
}
