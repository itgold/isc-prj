package com.iscweb.integration.doors.service.state;

import com.google.common.collect.Sets;
import com.iscweb.common.events.integration.door.DoorStateType;
import com.iscweb.common.model.IStateProducer;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.metadata.DoorOnlineStatus;
import com.iscweb.common.model.metadata.DoorTamperStatus;
import com.iscweb.integration.doors.models.SaltoStreamEventDto;
import com.iscweb.integration.doors.models.enums.Operation;
import com.iscweb.integration.doors.utils.SaltoUtils;

import java.util.Set;

/**
 * Generate door intrusion sub-state.
 */
public class SaltoIntrusionStateProducer implements IStateProducer<DeviceStateItemDto, DoorDto, SaltoStreamEventDto> {

    @Override
    public boolean hasStateData(SaltoStreamEventDto saltoStreamEventDto) {
        final Set<Operation> stateEvents = Sets.newHashSet(
                Operation.DURESS_ALARM,                                         // ???
                Operation.ALARM_INTRUSION_ONLINE,                               // INTRUSION start
                Operation.ALARM_TAMPER_ONLINE,                                  // TEMPER start
                Operation.DOOR_LEFT_OPENED_DLO,                                 // DOOR_LEFT_OPEN start
                Operation.END_OF_DLO_DOOR_LEFT_OPENED,                          // DOOR_LEFT_OPEN end
                Operation.END_OF_INTRUSION,                                     // INTRUSION end
                Operation.END_OF_TAMPER,                                        // TEMPER end
                Operation.SYNC
        );

        return stateEvents.contains(saltoStreamEventDto.getOperation());
    }

    @Override
    public DeviceStateItemDto process(DeviceStateItemDto oldState, DoorDto model, SaltoStreamEventDto saltoStreamEventDto) {
        DeviceStateItemDto stateItem = null;

        switch(saltoStreamEventDto.getOperation()) {
            case DURESS_ALARM:
            case ALARM_INTRUSION_ONLINE:
            case ALARM_TAMPER_ONLINE:
            case DOOR_LEFT_OPENED_DLO:
                stateItem = DeviceStateItemDto.builder().type(DoorStateType.INTRUSION.name()).value(saltoStreamEventDto.getOperation().name()).build();
                break;
            case END_OF_DLO_DOOR_LEFT_OPENED:
            case END_OF_INTRUSION:
            case END_OF_TAMPER:
                stateItem = null;
                break;
            case SYNC:
                stateItem = resolveStateFromModel(model);
                break;
            default:
                if (SaltoUtils.isEmpty(oldState)) {
                    stateItem = resolveStateFromModel(model);
                } else {
                    stateItem = oldState;
                }
                break;
        }

        return stateItem;
    }

    private DeviceStateItemDto resolveStateFromModel(DoorDto model) {
        DeviceStateItemDto stateItem = null;

        if (model != null && model.getTamperStatus() == DoorTamperStatus.ALARMED) {
            stateItem = DeviceStateItemDto.builder().type(DoorStateType.INTRUSION.name()).value(Operation.ALARM_TAMPER_ONLINE.name()).build();
        } else if (model != null && model.getOnlineStatus() == DoorOnlineStatus.INTRUSION) {
            stateItem = DeviceStateItemDto.builder().type(DoorStateType.INTRUSION.name()).value(Operation.ALARM_INTRUSION_ONLINE.name()).build();
        } else if (model != null && model.getOnlineStatus() == DoorOnlineStatus.LEFT_OPENED) {
            stateItem = DeviceStateItemDto.builder().type(DoorStateType.INTRUSION.name()).value(Operation.DOOR_LEFT_OPENED_DLO.name()).build();
        }

        return stateItem;
    }
}
