package com.iscweb.common.model;

/**
 * Interface need to be implemented by device state calculator.
 *
 * @param <STATE> Device state type
 * @param <MODEL_DATA> Device dto object type
 * @param <EVENT_DATA> Device event type
 */
public interface IStateProducer<STATE, MODEL_DATA, EVENT_DATA> {

    /**
     * Check if the device event cause a device state change.
     *
     * @param eventData Device event
     * @return <code>true</code> if the provided event has state data.
     */
    boolean hasStateData(EVENT_DATA eventData);

    /**
     * Process device event to produce new aggregated device state.
     *
     * @param oldState Old device state
     * @param model Device dto object
     * @param eventData Device event
     * @return New device state
     */
    STATE process(STATE oldState, MODEL_DATA model, EVENT_DATA eventData);

}
