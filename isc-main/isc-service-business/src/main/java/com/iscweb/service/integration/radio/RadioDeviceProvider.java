package com.iscweb.service.integration.radio;

import com.google.common.collect.Sets;
import com.iscweb.common.events.integration.IExternalEntityProvider;
import com.iscweb.common.exception.ServiceException;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IDeviceStateDto;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.LazyLoadingField;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.common.model.event.radio.RadioStateEvent;
import com.iscweb.common.model.metadata.RadioStatus;
import com.iscweb.persistence.model.jpa.RadioJpa;
import com.iscweb.service.RadioService;
import com.iscweb.service.converter.Convert;
import com.iscweb.service.converter.Scope;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Helper service provides CRUD operations with radio devices unified way in event processing pipeline.
 */
@Slf4j
@Component
public class RadioDeviceProvider implements IExternalEntityProvider {

    @Getter
    @Setter(onMethod = @__({@Autowired}))
    private RadioService radioService;

    @Override
    public IExternalEntityDto resolveEntityById(String deviceId) {
        return getRadioService().findByGuid(deviceId, List.of(LazyLoadingField.DEVICE_STATE, LazyLoadingField.PARENT_REGION));
    }

    @Override
    public IExternalEntityDto resolveEntityByExternalId(String externalId) {
        IExternalEntityDto device = null;
        if (!StringUtils.isEmpty(externalId)) {
            RadioJpa radio = getRadioService().findByExternalId(externalId, List.of(LazyLoadingField.DEVICE_STATE, LazyLoadingField.PARENT_REGION));
            if (radio != null) {
                device = Convert.my(radio).scope(Scope.ALL).boom();
            }
        }

        return device;
    }

    @Override
    public IDeviceStateDto resolveDeviceState(IExternalEntityDto device) {
        IDeviceStateDto deviceState = new RadioStateEvent.RadioDeviceStatePayload();
        Set<DeviceStateItemDto> state = device != null && ((RadioDto) device).getState() != null ?
                                        Sets.newHashSet(((RadioDto) device).getState()
                                                                          .stream()
                                                                          .map(s -> new DeviceStateItemDto(s.getType(), s.getValue(), s.getUpdated()))
                                                                          .collect(Collectors.toList()))
                                                                                                : null;
        if (state != null) {
            deviceState = new RadioStateEvent.RadioDeviceStatePayload(device.getEntityId(), state);
        }

        return deviceState;
    }

    @Override
    public IExternalEntityDto update(IExternalEntityDto device, IExternalEntityDto updatedDevice, IDeviceStateDto updatedDeviceState) throws ServiceException {
        IExternalEntityDto result = updatedDevice != null ? updatedDevice : device;

        if (updatedDevice != null) {
            RadioDto radio = (RadioDto) updatedDevice;
            if (updatedDeviceState != null) {
                radio.setState(Sets.newHashSet(updatedDeviceState.getState()));
            }
            radio.setLastSyncTime(result.getLastSyncTime());
            if (device == null) { //it is a new radio
                radio.setCreated(radio.getUpdated() != null ? radio.getUpdated() : ZonedDateTime.now());
                radio.setUpdated(radio.getCreated());
                if (radio.getStatus() == null) {
                    radio.setStatus(RadioStatus.ACTIVATED);
                }

                // Uncomment it if we need to resolve parents based on naming convention
                // Please look on DeviceMetaResolver::resolve and DoorDeviceMeta as an example
                // radio.setParentIds(Sets.newHashSet(getRadioService().resolveParentRegion(EntityType.RADIO, radio.getName())));

                result = getRadioService().create(radio, null);
            } else { //this is an update operation
                // Uncomment it if we need to resolve parents based on naming convention
                // Please look on DeviceMetaResolver::resolve and DoorDeviceMeta as an example
                // if (radio.getParentIds().isEmpty()) { //only resolve parents automatically when no parrents associated
                //     radio.setParentIds(Sets.newHashSet(getRadioService().resolveParentRegion(EntityType.RADIO, radio.getName())));
                // }
                result = getRadioService().update(radio, null);
            }
        }

        return result;
    }
}
