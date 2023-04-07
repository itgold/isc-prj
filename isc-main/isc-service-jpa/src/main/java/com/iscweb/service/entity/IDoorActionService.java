package com.iscweb.service.entity;

import com.iscweb.common.model.dto.DeviceActionResultDto;

import java.util.Set;

public interface IDoorActionService extends IDeviceActionService {
    DeviceActionResultDto emergencyClose(Set<String> deviceIds);

    DeviceActionResultDto endEmergencyMode(Set<String> newHashSet);

    DeviceActionResultDto emergencyOpen(Set<String> newHashSet);

    DeviceActionResultDto openDoor(Set<String> newHashSet);
}
