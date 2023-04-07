package com.iscweb.integration.doors.service.state;

import com.google.common.collect.Sets;
import com.iscweb.common.events.integration.door.DoorStateType;
import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.metadata.DoorConnectionStatus;
import com.iscweb.integration.doors.models.SaltoStreamEventDto;
import com.iscweb.integration.doors.models.enums.Operation;
import com.iscweb.integration.doors.utils.SaltoUtils;

import java.util.Set;

/**
 * Generate door connectivity status sub-state.
 */
public class SaltoConnectionStateProducer implements IStateProducer<DeviceStateItemDto, DoorDto, SaltoStreamEventDto> {

    @Override
    public boolean hasStateData(SaltoStreamEventDto saltoStreamEventDto) {
        final Set<Operation> stateEvents = Sets.newHashSet(
                Operation.COMMUNICATION_WITH_THE_READER_LOST,                   // CONNECTION lost
                Operation.COMMUNICATION_WITH_THE_READER_REESTABLISHED,          // CONNECTION established
                Operation.COMMUNICATION_WITH_SALTO_SOFTWARE_LOST,               // CONNECTION lost
                Operation.COMMUNICATION_WITH_SALTO_SOFTWARE_ESTABLISHED,        // CONNECTION established
                Operation.COMMUNICATION_REESTABLISHED,                          // CONNECTION established
                Operation.COMMUNICATION_LOST,                                   // CONNECTION lost
                Operation.SYNC
        );

        return stateEvents.contains(saltoStreamEventDto.getOperation());
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, DoorDto model, SaltoStreamEventDto saltoStreamEventDto) {
        DoorConnectionStatus modelStatus = model != null ? model.getConnectionStatus() : DoorConnectionStatus.UNKNOWN;
        DoorConnectionStatus status = SaltoUtils.isEmpty(oldState) ? modelStatus : DoorConnectionStatus.valueOf(oldState.getValue());

        if (saltoStreamEventDto.getOperation() == Operation.COMMUNICATION_WITH_THE_READER_LOST ||
                saltoStreamEventDto.getOperation() == Operation.COMMUNICATION_WITH_SALTO_SOFTWARE_LOST ||
                saltoStreamEventDto.getOperation() == Operation.COMMUNICATION_LOST) {
            status = DoorConnectionStatus.NO_COMMUNICATION;
        } else if (saltoStreamEventDto.getOperation() == Operation.SYNC) {
            status = model.getConnectionStatus();
        }

        return status != null ? DeviceStateItemDto.builder().type(DoorStateType.COMMUNICATION.name()).value(status.name()).build() : null;
    }
}
