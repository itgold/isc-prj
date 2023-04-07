package com.iscweb.service.integration.drone;

import com.google.common.collect.Sets;
import com.iscweb.common.events.integration.IExternalEntityProvider;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.IDeviceStateDto;
import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.DroneDto;
import com.iscweb.common.model.entity.IDrone;
import com.iscweb.common.model.event.drone.DroneStateEvent;
import com.iscweb.service.DroneService;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DroneDeviceProvider implements IExternalEntityProvider {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private DroneService droneService;

    @Override
    public IExternalEntityDto resolveEntityById(String deviceId) {
        return getDroneService().findByGuid(deviceId, List.of(LazyLoadingField.DEVICE_STATE, LazyLoadingField.PARENT_REGION));
    }

    @Override
    public IExternalEntityDto resolveEntityByExternalId(String externalId) {
        IExternalEntityDto device = null;
        IDrone drone = getDroneService().findByExternalId(externalId, List.of(LazyLoadingField.DEVICE_STATE));
        if (drone != null) {
            device = Convert.my(drone).scope(Scope.METADATA).boom();
        }

        return device;
    }

    @Override
    //todo: resolve generics type erasure
    public IDeviceStateDto resolveDeviceState(IExternalEntityDto device) {
        IDeviceStateDto deviceState = new DroneStateEvent.DroneDeviceStatePayload();
        Set<DeviceStateItemDto> state = device != null && ((DroneDto) device).getState() != null ?
                Sets.newHashSet( ((DroneDto) device).getState()
                        .stream()
                        .map(s -> new DeviceStateItemDto(s.getType(), s.getValue(), s.getUpdated()))
                        .collect(Collectors.toList()) )
                : null;
        if (state != null) {
            deviceState = new DroneStateEvent.DroneDeviceStatePayload(device.getEntityId(), state);
        }

        return deviceState;
    }

    @Override
    public IExternalEntityDto update(IExternalEntityDto device, IExternalEntityDto updatedDevice, IDeviceStateDto updatedDeviceState) throws ServiceException {
        IExternalEntityDto updated = updatedDevice != null ? updatedDevice : device;
        if (updatedDevice != null) {
            DroneDto dto = (DroneDto) updatedDevice;
            if (device == null) {
                if (updatedDeviceState != null) {
                    dto.setState(Sets.newHashSet(updatedDeviceState.getState()));
                }
                dto.setLastSyncTime(updated.getLastSyncTime());
                updated = getDroneService().create(dto, null);
            } else {
                if (updatedDeviceState != null) {
                    dto.setState(Sets.newHashSet(updatedDeviceState.getState()));
                }
                updated = getDroneService().update(dto, null);
            }
        }

        return updated;
    }
}
