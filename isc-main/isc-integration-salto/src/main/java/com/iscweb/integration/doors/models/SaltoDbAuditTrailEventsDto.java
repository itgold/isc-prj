package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SaltoDbAuditTrailEventsDto implements ISaltoDto {

    @JsonProperty("SaltoDBAuditTrailEvent")
    private List<SaltoDbAuditTrailEventDto> events;
}
