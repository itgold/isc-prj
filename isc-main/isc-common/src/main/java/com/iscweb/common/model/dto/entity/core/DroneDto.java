package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.BaseExternalEntityDto;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.DroneStatus;
import com.iscweb.common.model.metadata.DroneType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import java.util.Set;

/**
 * Drone DTO object.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DroneDto extends BaseExternalEntityDto {

    private DroneStatus status;
    private DroneType type;
    private GeoPointDto currentLocation;
    private Set<DeviceStateItemDto> state;

    /**
     * @see IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.DRONE;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.DRONE;
    }

    @Override
    public String getEntityId() {
        return getId();
    }
}
