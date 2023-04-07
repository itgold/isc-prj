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
 * Generate door rejections info sub-state.
 */
public class SaltoRejectionStateProducer implements IStateProducer<DeviceStateItemDto, DoorDto, SaltoStreamEventDto> {

    private static final long EXPIRATION = 10; // 10 sec

    private final Set<Operation> stateEvents = Sets.newHashSet(
            Operation.OPENING_NOT_ALLOWED_KEY_NO_ACTIVATED,
            Operation.OPENING_NOT_ALLOWED_KEY_EXPIRED,
            Operation.OPENING_NOT_ALLOWED_KEY_OUT_OF_DATE,
            Operation.OPENING_NOT_ALLOWED_KEY_NOT_ALLOWED_IN_THIS_DOOR,
            Operation.OPENING_NOT_ALLOWED_OUT_OF_TIME,
            Operation.OPENING_NOT_ALLOWED_KEY_DOES_NOT_OVERRIDE_PRIVACY,
            Operation.OPENING_NOT_ALLOWED_OLD_HOTEL_GUEST_KEY,
            Operation.OPENING_NOT_ALLOWED_HOTEL_GUEST_KEY_CANCELLED,
            Operation.OPENING_NOT_ALLOWED_ANTIPASSBACK,
            Operation.OPENING_NOT_ALLOWED_SECOND_DOUBLE_CARD_NOT_PRESENTED,
            Operation.OPENING_NOT_ALLOWED_NO_ASSOCIATED_AUTHORIZATION,
            Operation.OPENING_NOT_ALLOWED_INVALID_PIN,
            Operation.OPENING_NOT_ALLOWED_DOOR_IN_EMERGENCY_STATE,
            Operation.OPENING_NOT_ALLOWED_KEY_CANCELLED,
            Operation.OPENING_NOT_ALLOWED_UNIQUE_OPENING_KEY_ALREADY_USED,
            Operation.OPENING_NOT_ALLOWED_KEY_WITH_OLD_RENOVATION_NUMBER,
            Operation.OPENING_NOT_ALLOWED_RUN_OUT_OF_BATTERY,
            Operation.OPENING_NOT_ALLOWED_UNABLE_TO_AUDIT_ON_THE_KEY,
            Operation.OPENING_NOT_ALLOWED_LOCKER_OCCUPANCY_TIMEOUT,
            Operation.OPENING_NOT_ALLOWED_DENIED_BY_HOST,
            Operation.OPENING_NOT_ALLOWED_KEY_WITH_DATA_MANIPULATED,
            Operation.CLOSING_NOT_ALLOWED_DOOR_IN_EMERGENCY_STATE
    );

    @Override
    public boolean hasStateData(SaltoStreamEventDto saltoStreamEventDto) {
        return stateEvents.contains(saltoStreamEventDto.getOperation());
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, DoorDto model, SaltoStreamEventDto saltoStreamEventDto) {
        DeviceStateItemDto rejection = null;
        if (stateEvents.contains(saltoStreamEventDto.getOperation())) {
            rejection = DeviceStateItemDto.builder().type(DoorStateType.REJECTION.name()).value(saltoStreamEventDto.getOperation().name()).build();
        } else if (!SaltoUtils.isEmpty(oldState) && oldState.getUpdated().plus(EXPIRATION, ChronoUnit.SECONDS).isAfter(ZonedDateTime.now())) {
            rejection = oldState;
        }

        // todo(dmorozov): Think of how to make possible to show rejections on UI for short period of time.
        // We need to keep Rejection state for some time. Single rejection usually should not cause Alert .. but at least
        // it should be shown for the short period of time on UI. If we will not handle timeout/expiration properly then
        // any next event without rejection flag or device sync will override that state.
        // So, what we want to do? It probably will be easier to create Alert with expiration and automatic ack after some timeout.
        // and more over, it is probably bad to have hardcoded timeout in this class. Better to have configurable Alert.
        // Question 2#: How we will implement Alert to handle multiple consequent rejections?

        return rejection;
    }
}
