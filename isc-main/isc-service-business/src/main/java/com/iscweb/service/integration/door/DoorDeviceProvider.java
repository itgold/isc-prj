package com.iscweb.service.integration.door;

import com.google.common.collect.Sets;
import com.iscweb.common.events.integration.IExternalEntityProvider;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IDeviceStateDto;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.DoorDto;
import com.iscweb.common.model.entity.IDoor;
import com.iscweb.common.model.event.door.DoorStateEvent;
import com.iscweb.common.model.metadata.DoorStatus;
import com.iscweb.common.model.metadata.DoorType;
import com.iscweb.service.DoorService;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DoorDeviceProvider implements IExternalEntityProvider {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DoorService doorService;

    @Override
    public IExternalEntityDto resolveEntityById(String deviceId) {
        return getDoorService().findByGuid(deviceId, List.of(LazyLoadingField.DEVICE_STATE, LazyLoadingField.PARENT_REGION));
    }

    @Override
    public IExternalEntityDto resolveEntityByExternalId(String externalId) {
        IExternalEntityDto device = null;
        IDoor door = getDoorService().findByExternalId(externalId, List.of(LazyLoadingField.DEVICE_STATE, LazyLoadingField.PARENT_REGION));
        if (door != null) {
            device = Convert.my(door).scope(Scope.ALL).boom();
        }

        return device;
    }

    @Override
    public IDeviceStateDto resolveDeviceState(IExternalEntityDto device) {
        IDeviceStateDto deviceState = new DoorStateEvent.DoorDeviceStatePayload();
        Set<DeviceStateItemDto> state = device != null && ((DoorDto) device).getState() != null ?
                                        Sets.newHashSet(((DoorDto) device).getState()
                                                                          .stream()
                                                                          .map(s -> new DeviceStateItemDto(s.getType(), s.getValue(), s.getUpdated()))
                                                                          .collect(Collectors.toList()))
                                                                                                : null;
        if (state != null) {
            deviceState = new DoorStateEvent.DoorDeviceStatePayload(device.getEntityId(), state);
        }

        return deviceState;
    }

    @Override
    public IExternalEntityDto update(IExternalEntityDto device, IExternalEntityDto updatedDevice, IDeviceStateDto updatedDeviceState) throws ServiceException {
        IExternalEntityDto result = updatedDevice != null ? updatedDevice : device;

        if (updatedDevice != null) {
            DoorDto door = (DoorDto) updatedDevice;
            if (updatedDeviceState != null) {
                door.setState(Sets.newHashSet(updatedDeviceState.getState()));
            }
            door.setLastSyncTime(result.getLastSyncTime());
            if (device == null) { //it is a new door
                door.setCreated(door.getUpdated() != null ? door.getUpdated() : ZonedDateTime.now());
                door.setUpdated(door.getCreated());
                if (door.getStatus() == null) {
                    door.setStatus(DoorStatus.ACTIVATED);
                }
                if (door.getType() == null) {
                    door.setType(DoorType.INDOOR);
                }

                door.setParentIds(Sets.newHashSet(getDoorService().resolveParentRegion(EntityType.DOOR, door.getName())));
                result = getDoorService().create(door, null);
            } else { //this is an update operation
                if (door.getParentIds().isEmpty()) { //only resolve parents automatically when no parrents associated
                    door.setParentIds(Sets.newHashSet(getDoorService().resolveParentRegion(EntityType.DOOR, door.getName())));
                }
                result = getDoorService().update(door, null);
            }
        }

        return result;
    }
}
