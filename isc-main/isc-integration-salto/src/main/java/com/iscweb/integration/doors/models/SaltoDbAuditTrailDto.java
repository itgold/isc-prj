package com.iscweb.integration.doors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SaltoDbAuditTrailDto implements ISaltoDto {

    @JsonProperty("SaltoDBAuditTrail")
    private SaltoDbAuditTrailEventsDto eventsList;
}
