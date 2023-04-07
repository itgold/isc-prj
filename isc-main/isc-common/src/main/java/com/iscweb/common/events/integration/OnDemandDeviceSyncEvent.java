package com.iscweb.common.events.integration;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseApplicationEvent;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.util.EventUtils;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@EventPath(path = OnDemandDeviceSyncEvent.PATH)
public class OnDemandDeviceSyncEvent extends BaseApplicationEvent<OnDemandDeviceSyncPayload> {

    public static final String PATH = "onDemandSync";

    public OnDemandDeviceSyncEvent(String triggeredByUser, List<EntityType> payload, String jobId) {
        setPayload(new OnDemandDeviceSyncPayload(OnDemandDeviceSyncEvent.PATH, triggeredByUser, payload, jobId));
    }

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(OnDemandDeviceSyncEvent.PATH, super.getEventPath());
    }
}
