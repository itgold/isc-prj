package com.iscweb.integration.cameras.mock.services.state;

import com.iscweb.common.events.integration.camera.CameraStateType;
import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.integration.cameras.mock.services.streaming.dto.LiveStatusItem;
import com.iscweb.integration.cameras.mock.services.streaming.events.MipCameraStatusEventDto;

import java.time.ZonedDateTime;

/**
 * Camera live stream sub-state calculation
 */
public class MipLiveStateProducer implements IStateProducer<DeviceStateItemDto, CameraDto, MipCameraStatusEventDto> {

    public enum Live { LiveOn, LiveOff }

    @Override
    public boolean hasStateData(MipCameraStatusEventDto mipCameraStatusEventDto) {
        return mipCameraStatusEventDto.getStatus()
                .stream()
                .anyMatch(status -> status.statusType() == LiveStatusItem.StatusType.LiveFeedStopped
                        || status.statusType() == LiveStatusItem.StatusType.LiveFeedStarted
                        || status.statusType() == LiveStatusItem.StatusType.FeedWithMotion
                        || status.statusType() == LiveStatusItem.StatusType.FeedRecorded
                        || status.statusType() == LiveStatusItem.StatusType.SomeEvents
                        || status.statusType() == LiveStatusItem.StatusType.FeedRestored
                        || status.statusType() == LiveStatusItem.StatusType.FeedLost);
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, CameraDto model, MipCameraStatusEventDto mipCameraStatusEventDto) {
        DeviceStateItemDto newState = oldState;

        for (LiveStatusItem statusItem : mipCameraStatusEventDto.getStatus()) {
            if (LiveStatusItem.StatusType.LiveFeedStarted.getCode().equals(statusItem.getId())
                    || LiveStatusItem.StatusType.FeedWithMotion.getCode().equals(statusItem.getId())
                    || LiveStatusItem.StatusType.FeedRecorded.getCode().equals(statusItem.getId())
                    || LiveStatusItem.StatusType.SomeEvents.getCode().equals(statusItem.getId())
                    || LiveStatusItem.StatusType.FeedRestored.getCode().equals(statusItem.getId())) {
                newState = new DeviceStateItemDto(CameraStateType.LIVE.name(), Live.LiveOn.name(), ZonedDateTime.now());
                break;
            } else if (LiveStatusItem.StatusType.LiveFeedStopped.getCode().equals(statusItem.getId())
                    || LiveStatusItem.StatusType.FeedLost.getCode().equals(statusItem.getId())) {
                newState = new DeviceStateItemDto(CameraStateType.LIVE.name(), Live.LiveOff.name(), ZonedDateTime.now());
                break;
            }
        }

        return newState;
    }
}
