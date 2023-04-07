package com.iscweb.integration.cameras.mock.services.state;

import com.google.common.collect.Lists;
import com.iscweb.common.events.integration.camera.CameraStateType;
import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.integration.cameras.mock.services.streaming.dto.LiveStatusItem;
import com.iscweb.integration.cameras.mock.services.streaming.events.MipCameraStatusEventDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Camera hardware sub-state calculation.
 */
public class MipHardwareStateProducer implements IStateProducer<DeviceStateItemDto, CameraDto, MipCameraStatusEventDto> {

    @Override
    public boolean hasStateData(MipCameraStatusEventDto mipCameraStatusEventDto) {
        return mipCameraStatusEventDto.getStatus()
                .stream()
                .anyMatch(status -> status.statusType() == LiveStatusItem.StatusType.CameraMaintenance
                        || status.statusType() == LiveStatusItem.StatusType.DBAccessRestored
                        || status.statusType() == LiveStatusItem.StatusType.DBAccessLost
                        || status.statusType() == LiveStatusItem.StatusType.DiskSpaceOk
                        || status.statusType() == LiveStatusItem.StatusType.DiskSpaceOut);
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, CameraDto model, MipCameraStatusEventDto mipCameraStatusEventDto) {
        DeviceStateItemDto newState = oldState;

        List<String> flags = Lists.newArrayList();
        for (LiveStatusItem item : mipCameraStatusEventDto.getStatus()) {
            if (LiveStatusItem.StatusType.CameraMaintenance.getCode().equals(item.getId())) {
                flags.add(item.getValue());
            } else if (LiveStatusItem.StatusType.DBAccessLost.getCode().equals(item.getId())) {
                flags.add(LiveStatusItem.StatusType.DBAccessLost.name());
            } else if (LiveStatusItem.StatusType.DBAccessRestored.getCode().equals(item.getId())) {
                flags.add(LiveStatusItem.StatusType.DBAccessRestored.name());
            } else if (LiveStatusItem.StatusType.DiskSpaceOk.getCode().equals(item.getId())) {
                flags.add(LiveStatusItem.StatusType.DiskSpaceOk.name());
            } else if (LiveStatusItem.StatusType.DiskSpaceOut.getCode().equals(item.getId())) {
                flags.add(LiveStatusItem.StatusType.DiskSpaceOut.name());
            }
        }

        if (!CollectionUtils.isEmpty(flags)) {
            newState = new DeviceStateItemDto(CameraStateType.HARDWARE.name(), StringUtils.join(flags, ","), ZonedDateTime.now());
        }

        return newState;
    }
}
