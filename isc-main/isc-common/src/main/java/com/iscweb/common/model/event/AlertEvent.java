package com.iscweb.common.model.event;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.entity.core.AlertDto;
import com.iscweb.common.util.EventUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EventPath(path = AlertEvent.PATH)
public class AlertEvent extends BaseAlertEvent<AlertDto, AlertEvent.AlertEventPayload> {

    public static final String PATH = "newAlert";

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(AlertEvent.PATH, super.getEventPath());
    }

    @Override
    public EntityType getDeviceType() {
        return getPayload() != null ? getPayload().getDevice().getDeviceType() : null;
    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class AlertEventPayload extends DeviceIncrementalUpdatePayload<AlertDto> {
        public AlertEventPayload() {}
        public AlertEventPayload(AlertDto alertDto) {
            super(alertDto.getDeviceType().name(), alertDto.getDeviceId(), alertDto.getCode(), alertDto.getDescription(), alertDto);
        }
    }
}
