package com.iscweb.integration.radios.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iscweb.common.model.dto.IDto;
import com.iscweb.integration.radios.utils.TrboNetConstants;
import lombok.Data;

import java.util.Date;

/**
 * Radio device GPS information Dto.
 */
@Data
public class DeviceGPSInfoDto implements IDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TrboNetConstants.FORMAT_DATE_TIME)
    @JsonProperty("InfoDate")
    private Date infoDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TrboNetConstants.FORMAT_DATE_TIME)
    @JsonProperty("InfoDateUtc")
    private Date infoDateUtc;

    @JsonProperty("HasUtcDate")
    private boolean hasUtcDate;

    @JsonProperty("Latitude")
    private double latitude;

    @JsonProperty("Longitude")
    private double longitude;

    @JsonProperty("Altitude")
    private double altitude;

    @JsonProperty("Radius")
    private double radius;

    @JsonProperty("Direction")
    private int direction;

    @JsonProperty("Speed")
    private float speed;
}
