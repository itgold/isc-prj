package com.iscweb.common.sis.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iscweb.common.events.BaseIntegrationRawEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.service.IEventHub;
import com.iscweb.common.sis.ISisEventProcessorService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Base abstract class for event listening service.
 * Please note, this class will spawn new working thread to minimize backpressure of event processing from event source.
 *
 * The class lifecycle: CREATED -> startProcessor() -> STARTED (as thread) -> stopProcessor() -> STOPPED
 * Note: As soon as processor service stopped it can not be started again.
 * The stopping part is mostly intended for UnitTests when we create new processor for each unit test and release all
 * resources at the end of the test.
 *
 * @param <E> Device Event class type
 * @param <T> Device Event payload class type
 * @param <P> Raw Event payload type
 */
@Slf4j
public abstract class BaseSisEventListeningService<E extends BaseIntegrationRawEvent<T>, T extends ITypedPayload, P>
        extends Thread implements ISisEventProcessorService<P>, Closeable {

    private static final String THREAD_NAME = "EVENTS_LISTENER";

    @Getter
    private final IEventHub eventHub;

    @Getter
    private final ObjectMapper objectMapper;

    private final BlockingQueue<P> queue = new LinkedBlockingQueue<>();

    public BaseSisEventListeningService(IEventHub eventHub, final ObjectMapper objectMapper) {
        this.eventHub = eventHub;
        this.objectMapper = objectMapper;
    }

    /**
     * Parsing function which takes raw event and transform it into the one going to be posted to the global Event Bus.
     *
     * @param payload Raw event
     * @return Global Event object going to be passed to the global event bus.
     * @throws IOException Error to be thrown when unable to parse raw event into the global one.
     */
    protected abstract List<E> parseEvent(P payload) throws IOException;

    @Override
    public void process(P event) throws InterruptedException {
        queue.put(event);
    }

    public void run() {
        this.setName(THREAD_NAME);

        try {
            while (!interrupted()) {
                P payload = queue.poll(1000, TimeUnit.MILLISECONDS);
                if (payload != null) {
                    try {
                        log.info("New event: " + payload);
                        List<E> events = parseEvent(payload);
                        for (E event : events) {
                            getEventHub().post(event);
                        }
                    } catch (Exception e) {
                        // todo(dmorozov): maybe to try to send to DeadLetterQueue for manual processing later?
                        log.error("Unable to parse event: " + payload, e);
                    }
                }
            }
        } catch (InterruptedException e) {
            log.warn("Event processor was interrupted.", e);
        }
    }

    /**
     * Start processing.
     */
    public void startProcessor() {
        super.start();
    }

    /**
     * Stop processing.
     */
    public void stopProcessor() {
        Thread.currentThread().interrupt();
    }

    /**
     * Implementation of <code>Closeable</code> interface to be able to use this class in unit tests
     * using "try with resource" construct.
     */
    @Override
    public void close() {
        stopProcessor();
    }
}
