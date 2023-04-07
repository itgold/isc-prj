package com.iscweb.integration.doors.service.state;

import com.google.common.collect.Sets;
import com.iscweb.common.events.integration.door.DoorStateType;
import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.metadata.DoorBatteryStatus;
import com.iscweb.integration.doors.models.SaltoStreamEventDto;
import com.iscweb.integration.doors.models.enums.Operation;
import com.iscweb.integration.doors.utils.SaltoUtils;

import java.util.Set;

/**
 * Generate door battery sub-state.
 */
public class SaltoBatteryStateProducer implements IStateProducer<DeviceStateItemDto, DoorDto, SaltoStreamEventDto> {

    @Override
    public boolean hasStateData(SaltoStreamEventDto saltoStreamEventDto) {
        final Set<Operation> stateEvents = Sets.newHashSet(
                Operation.LOW_BATTERY_LEVEL,
                Operation.OPENING_NOT_ALLOWED_RUN_OUT_OF_BATTERY,
                Operation.SYNC
        );

        return stateEvents.contains(saltoStreamEventDto.getOperation());
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, DoorDto model, SaltoStreamEventDto saltoStreamEventDto) {
        DoorBatteryStatus modelStatus = model != null ? model.getBatteryStatus() : DoorBatteryStatus.UNKNOWN;
        DoorBatteryStatus status = SaltoUtils.isEmpty(oldState) ? modelStatus : DoorBatteryStatus.valueOf(oldState.getValue());
        if (saltoStreamEventDto.getOperation() == Operation.OPENING_NOT_ALLOWED_RUN_OUT_OF_BATTERY ||
                saltoStreamEventDto.getOperation() == Operation.LOW_BATTERY_LEVEL) {
            status = DoorBatteryStatus.VERY_LOW;
        } else if (saltoStreamEventDto.getOperation() == Operation.SYNC) {
            status = model.getBatteryStatus();
        }
        return status != null ? DeviceStateItemDto.builder().type(DoorStateType.BATTERY.name()).value(status.name()).build() : null;
    }
}
