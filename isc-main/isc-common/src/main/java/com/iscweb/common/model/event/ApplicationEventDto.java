package com.iscweb.common.model.event;

import com.iscweb.common.model.dto.IDto;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ApplicationEventDto<P extends ITypedPayload> implements IDto {
    private String deviceId;
    private String eventId;
    private String correlationId;
    private String referenceId;
    private P payload;
    private ZonedDateTime eventTime;
    private ZonedDateTime receivedTime;

    private String origin;
    private String schoolId;
    private String districtId;
    private String type;

    public static <P extends ITypedPayload> ApplicationEventDto fromEvent(IApplicationEvent<P> event) {
        ApplicationEventDto dto = new ApplicationEventDto();
        dto.setEventId(event.getEventId());
        dto.setCorrelationId(event.getCorrelationId());
        dto.setReferenceId(event.getReferenceId());
        dto.setEventTime(event.getEventTime());
        dto.setReceivedTime(event.getReceivedTime());
        dto.setPayload(event.getPayload());
        dto.setType(event.getEventPath());
        if (event instanceof IDeviceEvent) {
            dto.setDeviceId(((IDeviceEvent) event).getDeviceId());
        }

        return dto;
    }
}
