package com.iscweb.integration.doors.service.state;

import com.google.common.collect.Sets;
import com.iscweb.common.events.integration.door.DoorStateType;
import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.metadata.DoorOnlineStatus;
import com.iscweb.integration.doors.models.SaltoStreamEventDto;
import com.iscweb.integration.doors.models.enums.Operation;
import com.iscweb.integration.doors.utils.SaltoUtils;

import java.util.Set;

/**
 * Generate door open/close status sub-state.
 */
public class SaltoStatusStateProducer implements IStateProducer<DeviceStateItemDto, DoorDto, SaltoStreamEventDto> {

    private static final Set<Operation> OPEN_CLOSE_EVENTS = Sets.newHashSet(
            Operation.DOOR_OPENED_INSIDE_HANDLE,
            Operation.DOOR_OPENED_KEY,
            Operation.DOOR_OPENED_KEY_AND_KEYBOARD,
            Operation.DOOR_OPENED_MULTI_GUEST_KEY,
            Operation.DOOR_OPENED_UNIQUE,
            Operation.DOOR_OPENED_SWITCH,
            Operation.DOOR_OPENED_MECHANICAL_KEY,
            Operation.DOOR_OPENED_AFTER_SECOND_DOUBLE_CARD_PRESENTED,
            Operation.DOOR_OPENED_PPD,
            Operation.DOOR_OPENED_KEYBOARD,
            Operation.DOOR_OPENED_SPARE_CARD,
            Operation.DOOR_OPENED_ONLINE_COMMAND,
            Operation.DOOR_MOST_PROBABLY_OPENED_KEY_AND_PIN,
            Operation.DOOR_CLOSED_KEY,
            Operation.DOOR_CLOSED_KEY_AND_KEYBOARD,
            Operation.DOOR_CLOSED_KEYBOARD,
            Operation.DOOR_CLOSED_SWITCH
    );

    private static final Set<Operation> OTHER_EVENTS = Sets.newHashSet(
            Operation.DOOR_LEFT_OPENED_DLO,
            Operation.END_OF_DLO_DOOR_LEFT_OPENED,
            Operation.SYNC
    );

    @Override
    public boolean hasStateData(SaltoStreamEventDto saltoStreamEventDto) {
        return OPEN_CLOSE_EVENTS.contains(saltoStreamEventDto.getOperation()) || OTHER_EVENTS.contains(saltoStreamEventDto.getOperation());
    }

    public boolean isOpenCloseEvent(SaltoStreamEventDto saltoStreamEventDto) {
        return OPEN_CLOSE_EVENTS.contains(saltoStreamEventDto.getOperation());
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, DoorDto model, SaltoStreamEventDto saltoStreamEventDto) {
        DeviceStateItemDto stateItem;

        switch(saltoStreamEventDto.getOperation()) {
            case DOOR_OPENED_INSIDE_HANDLE:
            case DOOR_OPENED_KEY:
            case DOOR_OPENED_KEY_AND_KEYBOARD:
            case DOOR_OPENED_MULTI_GUEST_KEY:
            case DOOR_OPENED_UNIQUE:
            case DOOR_OPENED_SWITCH:
            case DOOR_OPENED_MECHANICAL_KEY:
            case DOOR_OPENED_AFTER_SECOND_DOUBLE_CARD_PRESENTED:
            case DOOR_OPENED_PPD:
            case DOOR_OPENED_KEYBOARD:
            case DOOR_OPENED_SPARE_CARD:
            case DOOR_OPENED_ONLINE_COMMAND:
            case DOOR_MOST_PROBABLY_OPENED_KEY_AND_PIN:
            case DOOR_LEFT_OPENED_DLO:
                stateItem = DeviceStateItemDto.builder().type(DoorStateType.STATUS.name()).value(DoorOnlineStatus.OPENED.name()).build();
                break;
            case DOOR_CLOSED_KEY:
            case DOOR_CLOSED_KEY_AND_KEYBOARD:
            case DOOR_CLOSED_KEYBOARD:
            case DOOR_CLOSED_SWITCH:
            case END_OF_DLO_DOOR_LEFT_OPENED:
                stateItem = DeviceStateItemDto.builder().type(DoorStateType.STATUS.name()).value(DoorOnlineStatus.CLOSED.name()).build();
                break;
            case SYNC:
                stateItem = statusFromModel(model, oldState);
                break;
            default:
                stateItem = statusFromModel(model, oldState);
                break;
        }

        return stateItem;
    }

    private DeviceStateItemDto statusFromModel(DoorDto model, DeviceStateItemDto oldState) {
        DeviceStateItemDto stateItem = null;

        if (SaltoUtils.isEmpty(oldState) && (model != null && model.getOnlineStatus() == DoorOnlineStatus.OPENED
                || model != null && model.getOnlineStatus() == DoorOnlineStatus.LEFT_OPENED
                || model != null && model.getOnlineStatus() == DoorOnlineStatus.EMERGENCY_OPEN)) {
            stateItem = DeviceStateItemDto.builder().type(DoorStateType.STATUS.name()).value(DoorOnlineStatus.OPENED.name()).build();
        } else if (SaltoUtils.isEmpty(oldState) && model != null && model.getOnlineStatus() == DoorOnlineStatus.CLOSED
                || model != null && model.getOnlineStatus() == DoorOnlineStatus.EMERGENCY_CLOSE) {
            stateItem = DeviceStateItemDto.builder().type(DoorStateType.STATUS.name()).value(DoorOnlineStatus.CLOSED.name()).build();
        } else {
            stateItem = oldState;
            if (stateItem == null) {
                if (model.getOnlineStatus() != null && model.getOnlineStatus() == DoorOnlineStatus.OPENED
                        || model.getOnlineStatus() == DoorOnlineStatus.LEFT_OPENED
                        || model.getOnlineStatus() == DoorOnlineStatus.INTRUSION
                        || model.getOnlineStatus() == DoorOnlineStatus.EMERGENCY_OPEN) {
                    stateItem = DeviceStateItemDto.builder().type(DoorStateType.STATUS.name()).value(DoorOnlineStatus.OPENED.name()).build();
                } else {
                    stateItem = DeviceStateItemDto.builder().type(DoorStateType.STATUS.name()).value(DoorOnlineStatus.CLOSED.name()).build();
                }
            }
        }

        return stateItem;
    }
}
