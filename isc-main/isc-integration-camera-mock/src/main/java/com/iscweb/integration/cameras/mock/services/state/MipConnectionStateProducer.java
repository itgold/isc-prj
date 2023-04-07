package com.iscweb.integration.cameras.mock.services.state;

import com.iscweb.common.events.integration.camera.CameraStateType;
import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.integration.cameras.mock.services.streaming.dto.LiveStatusItem;
import com.iscweb.integration.cameras.mock.services.streaming.events.MipCameraStatusEventDto;

import java.time.ZonedDateTime;

/**
 * Camera connection sub-state calculation.
 */
public class MipConnectionStateProducer implements IStateProducer<DeviceStateItemDto, CameraDto, MipCameraStatusEventDto> {

    @Override
    public boolean hasStateData(MipCameraStatusEventDto mipCameraStatusEventDto) {
        return mipCameraStatusEventDto.getStatus()
                .stream()
                .anyMatch(status -> status.statusType() == LiveStatusItem.StatusType.ConnectionRestored
                        || status.statusType() == LiveStatusItem.StatusType.ConnectionLost);
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, CameraDto model, MipCameraStatusEventDto mipCameraStatusEventDto) {
        DeviceStateItemDto newState = oldState;

        for (LiveStatusItem statusItem : mipCameraStatusEventDto.getStatus()) {
            LiveStatusItem.StatusType status = LiveStatusItem.StatusType.findByCode(statusItem.getId(), statusItem.getValue());
            if (LiveStatusItem.StatusType.ConnectionRestored == status
                    || LiveStatusItem.StatusType.FeedRestored == status) {
                newState = new DeviceStateItemDto(CameraStateType.CONNECTION.name(), LiveStatusItem.StatusType.ConnectionRestored.name(), ZonedDateTime.now());
                break;
            } else if (LiveStatusItem.StatusType.ConnectionLost == status
                    || LiveStatusItem.StatusType.FeedLost == status) {
                newState = new DeviceStateItemDto(CameraStateType.CONNECTION.name(), LiveStatusItem.StatusType.ConnectionLost.name(), ZonedDateTime.now());
                break;
            }
        }

        return newState;
    }
}
