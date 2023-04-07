package com.iscweb.integration.doors.service.state;

import com.google.common.collect.Sets;
import com.iscweb.common.events.integration.door.DoorStateType;
import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.integration.doors.models.SaltoStreamEventDto;
import com.iscweb.integration.doors.models.enums.Operation;
import com.iscweb.integration.doors.utils.SaltoUtils;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

/**
 * Generate door hardware flags sub-state.
 */
public class SaltoHardwareStateProducer implements IStateProducer<DeviceStateItemDto, DoorDto, SaltoStreamEventDto> {

    private static final long EXPIRATION = 10; // 10 sec

    private final Set<Operation> stateEvents = Sets.newHashSet(
            Operation.DOOR_PROGRAMMED_WITH_SPARE_KEY,                       // HARDWARE - INFO
            Operation.DOOR_OPENED_PPD,                                      // HARDWARE - INFO
            Operation.PPD_CONNECTION,                                       // HARDWARE - INFO
            Operation.INCORRECT_CLOCK_VALUE,                                // HARDWARE - WARNING
            Operation.UNABLE_TO_PERFORM_OPEN_CLOSE_HARDWARE_FAILURE         // HARDWARE - FAILURE
    );

    @Override
    public boolean hasStateData(SaltoStreamEventDto saltoStreamEventDto) {
        return stateEvents.contains(saltoStreamEventDto.getOperation());
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, DoorDto model, SaltoStreamEventDto saltoStreamEventDto) {
        DeviceStateItemDto BaseDeviceStateItemDto = null;
        if (stateEvents.contains(saltoStreamEventDto.getOperation())) {
            BaseDeviceStateItemDto = DeviceStateItemDto.builder().type(DoorStateType.HARDWARE.name()).value(saltoStreamEventDto.getOperation().name()).build();
        } else /* if (!SaltoUtils.isEmpty(oldState) && oldState.getUpdated().plus(EXPIRATION, ChronoUnit.SECONDS).isAfter(ZonedDateTime.now())) */ {
            BaseDeviceStateItemDto = oldState;
        }

        // todo(dmorozov): Think of how to make possible to show rejections on UI for short period of time.
        // We need to keep suspicious hardware events for some time. Such events should be shown for the short period of
        // time on UI. If we will not handle timeout/expiration properly then any next event without hardware flag or device sync will override that state.
        // So, what we want to do? It probably will be easier to create Alert with expiration and automatic ack after some timeout.
        // and more over, it is probably bad to have hardcoded timeout in this class. Better to have configurable Alert.
        // Question #2: can we have MULTIPLE suspicious hardware events???

        return BaseDeviceStateItemDto;
    }
}
