package com.iscweb.integration.doors.service.state;

import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.service.IDeviceAlertService;
import com.iscweb.integration.doors.models.SaltoStreamEventDto;

/**
 * Generate door device alerts based door sub-state.
 */
public class AlertsStateProducer implements IStateProducer<DeviceStateItemDto, DoorDto, SaltoStreamEventDto> {

    private final IDeviceAlertService alertService;

    public AlertsStateProducer(IDeviceAlertService alertService) {
        this.alertService = alertService;
    }

    @Override
    public boolean hasStateData(SaltoStreamEventDto saltoStreamEventDto) {
        return false;
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, DoorDto model, SaltoStreamEventDto saltoStreamEventDto) {
        return null;
    }
}
