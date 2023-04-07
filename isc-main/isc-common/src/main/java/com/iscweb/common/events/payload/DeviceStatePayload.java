package com.iscweb.common.events.payload;

import com.iscweb.common.model.IDeviceStateDto;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Base class for all device state event payloads.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceStatePayload implements IDeviceStateDto {

    private String type;

    private String deviceId;

    private Set<DeviceStateItemDto> state;

}
