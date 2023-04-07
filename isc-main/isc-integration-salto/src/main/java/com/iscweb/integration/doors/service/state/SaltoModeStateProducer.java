package com.iscweb.integration.doors.service.state;

import com.google.common.collect.Sets;
import com.iscweb.common.events.integration.door.DoorStateType;
import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.metadata.DoorOpeningMode;
import com.iscweb.integration.doors.models.SaltoStreamEventDto;
import com.iscweb.integration.doors.models.enums.Operation;
import com.iscweb.integration.doors.utils.SaltoUtils;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

/**
 * Generate door mode sub-state.
 */
public class SaltoModeStateProducer implements IStateProducer<DeviceStateItemDto, DoorDto, SaltoStreamEventDto> {

    private static final int EXPIRATION = 10; // 10 sec

    private enum Mode {
        FORCE_OPEN, FORCE_CLOSE
    }

    @Override
    public boolean hasStateData(SaltoStreamEventDto saltoStreamEventDto) {
        final Set<Operation> stateEvents = Sets.newHashSet(
                Operation.END_OF_OFFICE_MODE_KEYPAD,                            // MODE: OFFICE end
                Operation.START_OF_OFFICE_MODE,                                 // MODE: OFFICE start
                Operation.END_OF_OFFICE_MODE,                                   // MODE: OFFICE end
                Operation.START_OF_OFFICE_MODE_ONLINE,                          // MODE: OFFICE start
                Operation.END_OF_OFFICE_MODE_ONLINE,                            // MODE: OFFICE end
                Operation.START_OF_FORCE_OPEN_ONLINE,                           // MODE: FORCE_OPEN start
                Operation.END_OF_FORCE_OPEN_ONLINE,                             // MODE: FORCE_OPEN end
                Operation.START_OF_FORCED_CLOSING_ONLINE,                       // MODE: FORCED_CLOSING start
                Operation.END_OF_FORCED_CLOSING_ONLINE,                         // MODE: FORCED_CLOSING end
                Operation.SYNC
        );

        return stateEvents.contains(saltoStreamEventDto.getOperation());
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, DoorDto model, SaltoStreamEventDto saltoStreamEventDto) {
        DeviceStateItemDto stateItem;

        DoorOpeningMode openingMode = model != null && model.getOpeningMode() != null ? model.getOpeningMode() : DoorOpeningMode.STANDARD;

        if (Operation.START_OF_OFFICE_MODE == saltoStreamEventDto.getOperation()
                || Operation.START_OF_OFFICE_MODE_ONLINE == saltoStreamEventDto.getOperation()) {
            stateItem = DeviceStateItemDto.builder().type(DoorStateType.MODE.name()).value(DoorOpeningMode.OFFICE.name()).build();
        } else if (Operation.END_OF_OFFICE_MODE_KEYPAD == saltoStreamEventDto.getOperation()
                || Operation.END_OF_OFFICE_MODE == saltoStreamEventDto.getOperation()) {
            stateItem = DeviceStateItemDto.builder().type(DoorStateType.MODE.name()).value(openingMode.name()).build();
        } else if (Operation.START_OF_FORCE_OPEN_ONLINE == saltoStreamEventDto.getOperation()) {
            stateItem = DeviceStateItemDto.builder().type(DoorStateType.MODE.name()).value(Mode.FORCE_OPEN.name()).build();
        } else if (Operation.END_OF_FORCE_OPEN_ONLINE == saltoStreamEventDto.getOperation()) {
            stateItem = DeviceStateItemDto.builder().type(DoorStateType.MODE.name()).value(openingMode.name()).build();
        } else if (Operation.START_OF_FORCED_CLOSING_ONLINE == saltoStreamEventDto.getOperation()) {
            stateItem = DeviceStateItemDto.builder().type(DoorStateType.MODE.name()).value(Mode.FORCE_CLOSE.name()).build();
        } else if (Operation.END_OF_FORCED_CLOSING_ONLINE == saltoStreamEventDto.getOperation()) {
            stateItem = DeviceStateItemDto.builder().type(DoorStateType.MODE.name()).value(openingMode.name()).build();
        } else if (Operation.SYNC == saltoStreamEventDto.getOperation()) {
            stateItem = DeviceStateItemDto.builder().type(DoorStateType.MODE.name()).value(openingMode.name()).build();
        } else if (!SaltoUtils.isEmpty(oldState) && oldState.getUpdated().plus(EXPIRATION, ChronoUnit.SECONDS).isAfter(ZonedDateTime.now())) {
            stateItem = oldState;
        } else {
            stateItem = DeviceStateItemDto.builder().type(DoorStateType.MODE.name()).value(openingMode.name()).build();
        }

        return stateItem;
    }
}
