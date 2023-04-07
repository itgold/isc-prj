package com.iscweb.common.model.dto.entity.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iscweb.common.model.EntityType;
import com.iscweb.common.model.IApplicationModel;
import com.iscweb.common.model.IExternalEntityDto;
import com.iscweb.common.model.dto.DeviceStateItemDto;
import com.iscweb.common.model.dto.entity.BaseExternalEntityDto;
import com.iscweb.common.model.metadata.ConverterType;
import com.iscweb.common.model.metadata.RadioConnectionStatus;
import com.iscweb.common.model.metadata.RadioStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Radio DTO object.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RadioDto extends BaseExternalEntityDto {

    private Set<DeviceStateItemDto> state;
    private RadioStatus status;
    private Integer deviceState; // combination of RadioStateFlags
    private String type;
    private RadioConnectionStatus connectionStatus;
    private String radioUserId;
    private Integer batteryLevel;

    private Double gpsAltitude;
    private Integer gpsDirection;
    private ZonedDateTime gpsUpdateTime;

    /**
     * @see IApplicationModel#getConverterType()
     */
    @Override
    @Transient
    @JsonIgnore
    public ConverterType getConverterType() {
        return ConverterType.RADIO;
    }

    /**
     * @see IExternalEntityDto#getEntityType()
     */
    @Override
    public EntityType getEntityType() {
        return EntityType.RADIO;
    }

    /**
     * @see IExternalEntityDto#getEntityId()
     */
    @Override
    public String getEntityId() {
        return getId();
    }
}
