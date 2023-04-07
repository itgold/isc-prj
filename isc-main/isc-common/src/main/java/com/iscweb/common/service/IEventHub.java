package com.iscweb.common.service;

import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.event.IEvent;

/**
 * An interface for application services which have been secured and are accessible
 * exclusively by users with the Administrator role.
 */
public interface IEventHub extends IApplicationService {

    void post(IEvent<?> event) throws ServiceException;

    <T extends IEvent<?>> void register(Class<T> eventClazz, IEventSubscriber<?, ?> subscriber);

    <T extends IEvent<?>> void register(String eventPath, IEventSubscriber<?, ?> subscriber);
}
