package com.iscweb.common.sis;

/**
 * Sis event processing service.
 * Need to be used to delegate raw event processing to background working thread to minimize the event source back pressure.
 *
 * @param <P> Raw Event payload type
 */
public interface ISisEventProcessorService<P> {
    void process(P event) throws InterruptedException;
}
