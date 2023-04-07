package com.iscweb.common.model.event;

import com.iscweb.common.annotations.EventPath;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.dto.entity.core.AlertDto;
import com.iscweb.common.util.EventUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EventPath(path = AlertIgnoreEvent.PATH)
public class AlertIgnoreEvent extends BaseAlertEvent<AlertDto, AlertIgnoreEvent.AlertIgnoreEventPayload> {

    public static final String PATH = "ignore";

    @Override
    public String getEventPath() {
        return EventUtils.generatePath(AlertIgnoreEvent.PATH, super.getEventPath());
    }

    @Override
    public EntityType getDeviceType() {
        return getPayload() != null ? getPayload().getDevice().getDeviceType() : null;
    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class AlertIgnoreEventPayload extends DeviceIncrementalUpdatePayload<AlertDto> {
        private String user;
        private String notes;

        public AlertIgnoreEventPayload() {}
        public AlertIgnoreEventPayload(AlertDto alertDto, String notes, String userName) {
            super(alertDto.getDeviceType().name(), alertDto.getDeviceId(), alertDto.getCode(), alertDto.getDescription(), alertDto);

            this.user = userName;
            this.notes = notes;
        }
    }
}
