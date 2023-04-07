package com.iscweb.common.events;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.event.IIncrementalUpdateEvent;
import com.iscweb.common.model.event.IIncrementalUpdatePayload;
import com.iscweb.common.model.event.IServerEvent;
import com.iscweb.common.util.EventUtils;

@EventPath(path = IServerEvent.PATH)
public abstract class BaseServerEvent<T extends IIncrementalUpdatePayload> extends BaseApplicationEvent<T>
        implements IIncrementalUpdateEvent<T> {

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(IServerEvent.PATH, super.getEventPath());
    }

    @Override
    public String getDeviceId() {
        return null;
    }

    @Override
    public void setDeviceId(String deviceId) {
    }
}
