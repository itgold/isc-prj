package com.iscweb.integration.cameras.mock.services.state;

import com.iscweb.common.events.integration.camera.CameraStateType;
import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.integration.cameras.mock.services.streaming.dto.LiveStatusItem;
import com.iscweb.integration.cameras.mock.services.streaming.events.MipCameraStatusEventDto;

import java.time.ZonedDateTime;

/**
 * Camera live stream recording sub-state calculation
 */
public class MipRecordingStateProducer implements IStateProducer<DeviceStateItemDto, CameraDto, MipCameraStatusEventDto> {

    public enum Recording {On, Off}

    @Override
    public boolean hasStateData(MipCameraStatusEventDto mipCameraStatusEventDto) {
        return mipCameraStatusEventDto.getStatus()
                .stream()
                .anyMatch(status -> status.statusType() == LiveStatusItem.StatusType.FeedNotRecorded
                        || status.statusType() == LiveStatusItem.StatusType.FeedRecorded);
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, CameraDto model, MipCameraStatusEventDto mipCameraStatusEventDto) {
        DeviceStateItemDto newState = oldState;

        for (LiveStatusItem statusItem : mipCameraStatusEventDto.getStatus()) {
            if (LiveStatusItem.StatusType.FeedRecorded.getCode().equals(statusItem.getId())) {
                newState = new DeviceStateItemDto(CameraStateType.RECORDING.name(), Recording.On.name(), ZonedDateTime.now());
                break;
            } else if (LiveStatusItem.StatusType.FeedNotRecorded.getCode().equals(statusItem.getId())) {
                newState = new DeviceStateItemDto(CameraStateType.RECORDING.name(), Recording.Off.name(), ZonedDateTime.now());
                break;
            }
        }

        return newState;
    }
}
