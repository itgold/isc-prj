package com.iscweb.service.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * A latch implementation where countdowns are associated with IDs. It can be used to optimize
 * the order of execution of application threads.
 *
 * @author skurenkov
 */
@Slf4j
public class Latch {

    /**
     * A map of countdown latches grouped by Long id.
     */
    private static Map<Long, CountDownLatch> constellationLatch = Maps.newConcurrentMap();

    public synchronized void init(Long id) {
        CountDownLatch countDownLatch = constellationLatch.get(id);
        if (countDownLatch == null) {
            constellationLatch.put(id, new CountDownLatch(1));
        }
    }

    /**
     * Count down the latch associated with a specific ID.
     *
     * @param id latch id.
     */
    public static void countDown(Long id) {
        CountDownLatch countDownLatch = constellationLatch.get(id);
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    /**
     * Await the release of the latch associated with a specific id.
     *
     * @param id latch id.
     */
    public static void await(Long id) {
        CountDownLatch countDownLatch = constellationLatch.get(id);
        if (countDownLatch != null) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.warn("Constellation latch interrupted.");
            }
            constellationLatch.remove(id);
        }
    }
}
