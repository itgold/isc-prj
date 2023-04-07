package com.iscweb.integration.cameras.mip.services.listeners;

import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.util.StringUtils;

import java.io.Closeable;
import java.util.List;

/**
 * Abstract contract for Milestone events listening session implementation
 */
public interface IMipListeningSession extends Closeable {
    /**
     * @return Session id
     */
    String getSessionId();

    /**
     * @return Friendly name for session listener
     */
    String name();

    /**
     * @return Extra information for the session listener. For example, list of devices.
     */
    default String details() {
        return StringUtils.EMPTY;
    }

    /**
     * Process session events
     *
     * @return Collection of all events for the camera devices received from Milestone
     */
    List<IEvent<ITypedPayload>> process() throws InvalidMipSessionException;

    /**
     * Close current Milestone listening session
     */
    void close();
}
