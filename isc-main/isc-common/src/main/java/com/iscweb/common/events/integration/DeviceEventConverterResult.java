package com.iscweb.common.events.integration;

import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.IDeviceStateDto;
import com.iscweb.common.model.event.IEvent;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.common.service.integration.IDeviceEventProcessor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Defines result of specific device event processing operation.
 * It contains an updated state of the device, updated device state Dto object,
 * and all application events that needs to be submitted into the queue.
 *
 * @see IDeviceEventProcessor#process(IEvent, IExternalEntityDto, IDeviceStateDto)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceEventConverterResult {

     private IExternalEntityDto updatedDevice;

    private IDeviceStateDto updatedDeviceState;

    private List<IEvent<? extends ITypedPayload>> events;
}
