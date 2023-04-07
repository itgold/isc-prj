package com.iscweb.common.model;

import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.event.ITypedPayload;

import java.util.Set;

/**
 * Common device state DTO interface.
 *
 * todo(dmorozov): What if we have MULTIPLE substates of the same type? Multiple rejections, multiple suspicious hardware events, etc ...
 */
public interface IDeviceStateDto extends ITypedPayload {

    Set<DeviceStateItemDto> getState();

}
