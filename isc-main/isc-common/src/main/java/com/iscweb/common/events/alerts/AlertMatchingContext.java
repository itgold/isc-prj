package com.iscweb.common.events.alerts;

import com.iscweb.common.model.IDeviceStateDto;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.dto.IContextObject;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class AlertMatchingContext implements IContextObject {

    private ZonedDateTime dateTime = ZonedDateTime.now();

    private IEvent<ITypedPayload> event;

    private String deviceId;

    private IExternalEntityDto device;

    private IDeviceStateDto deviceState;
}
