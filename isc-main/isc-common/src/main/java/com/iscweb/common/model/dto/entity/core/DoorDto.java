package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.BaseExternalEntityDto;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.DoorBatteryStatus;
import com.iscweb.common.model.metadata.DoorConnectionStatus;
import com.iscweb.common.model.metadata.DoorOnlineStatus;
import com.iscweb.common.model.metadata.DoorOpeningMode;
import com.iscweb.common.model.metadata.DoorStatus;
import com.iscweb.common.model.metadata.DoorTamperStatus;
import com.iscweb.common.model.metadata.DoorType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import java.util.Set;

/**
 * Door DTO object.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DoorDto extends BaseExternalEntityDto {

    private DoorType type;
    private DoorConnectionStatus connectionStatus;
    private DoorOnlineStatus onlineStatus;
    private DoorBatteryStatus batteryStatus;
    private DoorTamperStatus tamperStatus;
    private DoorOpeningMode openingMode;
    private Integer batteryLevel;
    private Boolean updateRequired;

    private Set<DeviceStateItemDto> state;
    private DoorStatus status;

    /**
     * @see IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.DOOR;
    }

    /**
     * @see IExternalEntityDto#getEntityType()
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.DOOR;
    }

    /**
     * @see IExternalEntityDto#getEntityId()
     */
    @Override
    public String getEntityId() {
        return getId();
    }
}
