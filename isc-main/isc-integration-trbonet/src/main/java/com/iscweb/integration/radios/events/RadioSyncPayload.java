package com.iscweb.integration.radios.events;

import com.google.common.collect.Sets;
import com.iscweb.common.events.CommonEventTypes;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.core.GeoPointDto;
import com.iscweb.common.model.dto.entity.core.RadioDto;
import com.iscweb.common.model.event.ITypedPayload;
import com.iscweb.integration.radios.models.RadioDeviceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Radio device sync event payload.
 *
 * @see RadioSyncEvent
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RadioSyncPayload implements ITypedPayload {

    private RadioDeviceDto data;

    @Override
    public String getType() {
        return CommonEventTypes.RADIO_SYNC.code();
    }

    public RadioDto toRadioDto(IExternalEntityDto deviceDto) {
        RadioDto radio = deviceDto != null ? (RadioDto) deviceDto : new RadioDto();

        radio.setExternalId(String.valueOf(data.getId()));
        radio.setName(data.getName());
        radio.setDescription(data.getDescription());
        radio.setType(data.getDeviceType() != null ? data.getDeviceType().name() : null);
        radio.setDeviceState(data.getDeviceState());

        if (data.isHasGPS() && data.getGpsInfo() != null) {
            GeoPointDto geoLocation = radio.getGeoLocation();
            if (geoLocation == null) geoLocation = new GeoPointDto();
            geoLocation.setX(data.getGpsInfo().getLatitude());
            geoLocation.setY(data.getGpsInfo().getLongitude());
            radio.setGpsAltitude(data.getGpsInfo().getAltitude());
            radio.setGpsDirection(data.getGpsInfo().getDirection());
            if (data.getGpsInfo().getInfoDateUtc() != null)
                radio.setGpsUpdateTime(ZonedDateTime.ofInstant(data.getGpsInfo().getInfoDateUtc().toInstant(), ZoneId.of("Etc/UTC")));
            else
                radio.setGpsUpdateTime(ZonedDateTime.ofInstant(data.getGpsInfo().getInfoDate().toInstant(), ZoneId.systemDefault()));
            radio.setGeoLocation(geoLocation);
        }

        radio.setBatteryLevel((int) data.getBatteryLevel());
        radio.setRadioUserId(String.valueOf(data.getRadioUserId()));

        if (data.getState() != null) {
            radio.setConnectionStatus(data.getState().getConnectionStatus());
        }
        if (!CollectionUtils.isEmpty(data.getAlarms())) {
            Set<DeviceStateItemDto> state = Sets.newHashSet();
            data.getAlarms().stream().forEach(alarm -> {
                ZonedDateTime infoDate = ZonedDateTime.now();
                if (alarm.getGpsInfo() != null) {
                    if (alarm.getGpsInfo().getInfoDateUtc() != null)
                        radio.setGpsUpdateTime(ZonedDateTime.ofInstant(alarm.getGpsInfo().getInfoDateUtc().toInstant(), ZoneId.of("Etc/UTC")));
                    else
                        radio.setGpsUpdateTime(ZonedDateTime.ofInstant(alarm.getGpsInfo().getInfoDate().toInstant(), ZoneId.systemDefault()));
                }
                state.add(new DeviceStateItemDto(alarm.getDeviceStatusName(), alarm.getAlarmText(), infoDate));
            });
            radio.setState(state);
        }

        return radio;
    }
}
