package com.iscweb.integration.cameras.mock.services.state;

import com.iscweb.common.events.integration.camera.CameraStateType;
import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.integration.cameras.mock.services.streaming.dto.LiveStatusItem;
import com.iscweb.integration.cameras.mock.services.streaming.events.MipCameraStatusEventDto;

import java.time.ZonedDateTime;

/**
 * Camera live stream motion sub-state calculation
 */
public class MipMotionStateProducer implements IStateProducer<DeviceStateItemDto, CameraDto, MipCameraStatusEventDto> {

    public enum Motion {On, Off}

    @Override
    public boolean hasStateData(MipCameraStatusEventDto mipCameraStatusEventDto) {
        return mipCameraStatusEventDto.getStatus()
                .stream()
                .anyMatch(status -> status.statusType() == LiveStatusItem.StatusType.FeedNoMotion
                        || status.statusType() == LiveStatusItem.StatusType.FeedWithMotion);
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, CameraDto model, MipCameraStatusEventDto mipCameraStatusEventDto) {
        DeviceStateItemDto newState = oldState;

        for (LiveStatusItem statusItem : mipCameraStatusEventDto.getStatus()) {
            if (LiveStatusItem.StatusType.FeedWithMotion.getCode().equals(statusItem.getId())) {
                newState = new DeviceStateItemDto(CameraStateType.MOTION.name(), Motion.On.name(), ZonedDateTime.now());
                break;
            } else if (LiveStatusItem.StatusType.FeedNoMotion.getCode().equals(statusItem.getId())) {
                newState = new DeviceStateItemDto(CameraStateType.MOTION.name(), Motion.Off.name(), ZonedDateTime.now());
                break;
            }
        }

        return newState;
    }
}
