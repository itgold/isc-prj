package com.iscweb.common.model.event;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.events.BaseApplicationAlert;
import com.iscweb.common.model.dto.BaseEntityDto;
import com.iscweb.common.util.EventUtils;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EventPath(path = BaseAlertEvent.PATH)
public abstract class BaseAlertEvent<DTO extends BaseEntityDto, T extends DeviceIncrementalUpdatePayload<DTO>> extends BaseApplicationAlert<T> implements IIncrementalUpdateEvent<T> {
    public static final String PATH = IIncrementalUpdateEvent.PATH;

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(BaseAlertEvent.PATH, super.getEventPath());
    }

    @Override
    public String getDeviceId() {
        return getPayload() != null ? getPayload().getDeviceId() : null;
    }

    @Override
    public void setDeviceId(String deviceId) {
        if(getPayload() != null) {
            getPayload().setDeviceId(deviceId);
        }
    }
}
