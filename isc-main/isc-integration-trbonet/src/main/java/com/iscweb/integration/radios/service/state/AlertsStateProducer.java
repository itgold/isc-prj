package com.iscweb.integration.radios.service.state;

import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.common.service.IDeviceAlertService;
import com.iscweb.integration.radios.events.TrboNetStreamEventDto;

/**
 * Generate radio device alerts based on radio sub-state.
 */
public class AlertsStateProducer implements IStateProducer<DeviceStateItemDto, RadioDto, TrboNetStreamEventDto> {

    private final IDeviceAlertService alertService;

    public AlertsStateProducer(IDeviceAlertService alertService) {
        this.alertService = alertService;
    }

    @Override
    public boolean hasStateData(TrboNetStreamEventDto radioDevice) {
        return false;
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, RadioDto model, TrboNetStreamEventDto radioDevice) {
        return null;
    }
}
