package com.iscweb.common.model.event;

/**
 * Marker interface need to be implemented by all payloads for incremental update events.
 *
 * @see com.iscweb.common.model.event.IIncrementalUpdateEvent
 */
public interface IIncrementalUpdatePayload extends ITypedPayload {
    String getNotes();
    String getUser();
}
