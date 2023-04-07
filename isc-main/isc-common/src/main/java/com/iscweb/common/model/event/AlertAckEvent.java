package com.iscweb.common.model.event;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.entity.core.AlertDto;
import com.iscweb.common.util.EventUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EventPath(path = AlertAckEvent.PATH)
public class AlertAckEvent extends BaseAlertEvent<AlertDto, AlertAckEvent.AlertAckEventPayload> {

    public static final String PATH = "ack";

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(AlertAckEvent.PATH, super.getEventPath());
    }

    @Override
    public EntityType getDeviceType() {
        return getPayload() != null ? getPayload().getDevice().getDeviceType() : null;
    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class AlertAckEventPayload extends DeviceIncrementalUpdatePayload<AlertDto> {
        private String user;
        private String notes;

        public AlertAckEventPayload() {}
        public AlertAckEventPayload(AlertDto alertDto, String notes, String userName) {
            super(alertDto.getDeviceType().name(), alertDto.getDeviceId(), alertDto.getCode(), alertDto.getDescription(), alertDto);

            this.user = userName;
            this.notes = notes;
        }
    }
}
