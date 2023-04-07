package com.iscweb.common.service;

import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;

public interface IEventSubscriber<E extends IEvent<P>, P extends ITypedPayload> {

    void handleApplicationEvent(E event);
}
