package com.iscweb.service.integration.camera;

import com.google.common.collect.Sets;
import com.iscweb.common.events.integration.IExternalEntityProvider;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.IDeviceStateDto;
import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.CameraDto;
import com.iscweb.common.model.entity.ICamera;
import com.iscweb.common.model.event.camera.CameraStateEvent;
import com.iscweb.service.CameraService;
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
public class CameraDeviceProvider implements IExternalEntityProvider {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private CameraService cameraService;

    @Override
    public IExternalEntityDto resolveEntityById(String deviceId) {
        return getCameraService().findByGuid(deviceId, List.of(LazyLoadingField.DEVICE_STATE, LazyLoadingField.PARENT_REGION));
    }

    @Override
    public IExternalEntityDto resolveEntityByExternalId(String externalId) {
        IExternalEntityDto device = null;
        ICamera camera = getCameraService().findByExternalId(externalId, List.of(LazyLoadingField.DEVICE_STATE, LazyLoadingField.PARENT_REGION));
        if (camera != null) {
            device = Convert.my(camera).scope(Scope.ALL).boom();
        }

        return device;
    }

    @Override
    public IDeviceStateDto resolveDeviceState(IExternalEntityDto device) {
        IDeviceStateDto deviceState = new CameraStateEvent.CameraDeviceStatePayload();
        Set<DeviceStateItemDto> state = device != null && ((CameraDto) device).getState() != null ?
                                        Sets.newHashSet(((CameraDto) device).getState()
                                                                            .stream()
                                                                            .map(s -> new DeviceStateItemDto(s.getType(), s.getValue(), s.getUpdated()))
                                                                            .collect(Collectors.toList())) :
                                        null;
        if (state != null) {
            deviceState = new CameraStateEvent.CameraDeviceStatePayload(device.getEntityId(), state);
        }

        return deviceState;
    }

    @Override
    public IExternalEntityDto update(IExternalEntityDto device, IExternalEntityDto updatedDevice, IDeviceStateDto updatedDeviceState) throws ServiceException {
        IExternalEntityDto updated = updatedDevice != null ? updatedDevice : device;
        if (updatedDevice != null) {
            CameraDto dto = (CameraDto) updatedDevice;
            if (device == null) {
                if (updatedDeviceState != null) {
                    dto.setState(Sets.newHashSet(updatedDeviceState.getState()));
                }
                dto.setLastSyncTime(updated.getLastSyncTime());
                updated = getCameraService().create(dto, null);
            } else {
                if (updatedDeviceState != null) {
                    dto.setState(Sets.newHashSet(updatedDeviceState.getState()));
                }
                updated = getCameraService().update(dto, null);
            }
        }

        return updated;
    }
}
