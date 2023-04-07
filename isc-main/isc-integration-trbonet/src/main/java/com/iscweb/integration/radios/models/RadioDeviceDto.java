package com.iscweb.integration.radios.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iscweb.common.model.dto.IDto;
import com.iscweb.integration.radios.utils.TrboNetConstants;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * TRBOnet radio device model.
 */
@Data
public class RadioDeviceDto implements IDto {

    private long id;
    private String name;
    private String description;
    private DeviceTypes deviceType;
    private byte deviceSubType;
    private int deviceState; // combination of DeviceState
    private UiDeviceState state;

    private int radioId;
    private int radioUserId;
    private byte batteryLevel;

    private GPSType gpsType;
    private boolean hasGPS;
    private boolean gpsEnabled;
    private DeviceGPSInfoDto gpsInfo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TrboNetConstants.FORMAT_DATE_TIME)
    private Date gpsResetDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TrboNetConstants.FORMAT_DATE_TIME)
    private Date gpsStartDate;
    private String gpsErrorText;

    private List<DeviceAlarmDto> alarms;
}
